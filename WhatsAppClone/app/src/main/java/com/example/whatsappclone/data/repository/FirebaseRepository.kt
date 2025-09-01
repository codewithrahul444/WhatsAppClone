package com.example.whatsappclone.data.repository

import android.content.Context
import com.example.whatsappclone.data.model.Chat
import com.example.whatsappclone.data.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val messaging: FirebaseMessaging,
    @ApplicationContext private val context: Context
) {
    
    fun getChatsFlow(): Flow<List<Chat>> = callbackFlow {
        val userId = auth.currentUser?.uid ?: return@callbackFlow
        
        val listener = firestore.collection("chats")
            .whereArrayContains("participants", userId)
            .orderBy("lastMessageTime", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, _ ->
                val chats = snapshot?.documents?.mapNotNull { doc ->
                    try {
                        Chat(
                            id = doc.id,
                            name = doc.getString("name") ?: "",
                            profileImage = doc.getString("profileImage"),
                            lastMessage = doc.getString("lastMessage") ?: "",
                            lastMessageTime = doc.getLong("lastMessageTime")?.let { 
                                Instant.fromEpochMilliseconds(it).toLocalDateTime(TimeZone.currentSystemDefault())
                            },
                            unreadCount = doc.getLong("unreadCount")?.toInt() ?: 0
                        )
                    } catch (e: Exception) { null }
                } ?: emptyList()
                
                trySend(chats)
            }
        
        awaitClose { listener.remove() }
    }
    
    fun getMessagesFlow(chatId: String): Flow<List<Message>> = callbackFlow {
        val listener = firestore.collection("chats")
            .document(chatId)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, _ ->
                val messages = snapshot?.documents?.mapNotNull { doc ->
                    try {
                        Message(
                            id = doc.id,
                            chatId = chatId,
                            content = doc.getString("content") ?: "",
                            timestamp = doc.getLong("timestamp")?.let {
                                Instant.fromEpochMilliseconds(it).toLocalDateTime(TimeZone.currentSystemDefault())
                            } ?: return@mapNotNull null,
                            isFromMe = doc.getString("senderId") == auth.currentUser?.uid
                        )
                    } catch (e: Exception) { null }
                } ?: emptyList()
                
                trySend(messages)
            }
        
        awaitClose { listener.remove() }
    }
    
    suspend fun sendMessage(chatId: String, content: String) {
        val userId = auth.currentUser?.uid ?: return
        val timestamp = System.currentTimeMillis()
        
        val message = hashMapOf(
            "content" to content,
            "senderId" to userId,
            "timestamp" to timestamp
        )
        
        // Add message to subcollection
        firestore.collection("chats")
            .document(chatId)
            .collection("messages")
            .add(message)
            .await()
        
        // Update chat's last message
        firestore.collection("chats")
            .document(chatId)
            .update(
                "lastMessage", content,
                "lastMessageTime", timestamp
            )
            .await()
        
        // Send push notification to other participants
        sendNotificationToParticipants(chatId, content)
    }
    
    private suspend fun sendNotificationToParticipants(chatId: String, message: String) {
        try {
            val chatDoc = firestore.collection("chats").document(chatId).get().await()
            val participants = chatDoc.get("participants") as? List<String> ?: return
            val chatName = chatDoc.getString("name") ?: "Chat"
            val currentUserId = auth.currentUser?.uid
            
            // Get FCM tokens for other participants
            participants.filter { it != currentUserId }.forEach { participantId ->
                val userDoc = firestore.collection("users").document(participantId).get().await()
                val fcmToken = userDoc.getString("fcmToken")
                
                fcmToken?.let { token ->
                    // In a real app, you'd send this to your backend server
                    // which would use Firebase Admin SDK to send notifications
                    // For now, we'll just store the notification data
                    val notificationData = hashMapOf(
                        "title" to chatName,
                        "body" to message,
                        "chatId" to chatId,
                        "timestamp" to System.currentTimeMillis()
                    )
                    
                    firestore.collection("notifications")
                        .add(notificationData)
                }
            }
        } catch (e: Exception) {
            // Handle error silently
        }
    }
    
    suspend fun createChat(participantId: String, name: String): String {
        val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")
        
        val chat = hashMapOf(
            "name" to name,
            "participants" to listOf(userId, participantId),
            "lastMessage" to "",
            "lastMessageTime" to System.currentTimeMillis(),
            "unreadCount" to 0
        )
        
        val docRef = firestore.collection("chats").add(chat).await()
        return docRef.id
    }
    
    suspend fun signInAnonymously(): Boolean {
        return try {
            auth.signInAnonymously().await()
            updateFCMToken()
            true
        } catch (e: Exception) {
            false
        }
    }
    
    private suspend fun updateFCMToken() {
        try {
            val token = messaging.token.await()
            val userId = auth.currentUser?.uid ?: return
            
            firestore.collection("users")
                .document(userId)
                .update("fcmToken", token)
                .await()
                
            // Save token locally
            val prefs = context.getSharedPreferences("fcm_prefs", Context.MODE_PRIVATE)
            prefs.edit().putString("fcm_token", token).apply()
        } catch (e: Exception) {
            // Handle error
        }
    }
    
    suspend fun initializeUser() {
        val userId = auth.currentUser?.uid ?: return
        val token = messaging.token.await()
        
        val userData = hashMapOf(
            "fcmToken" to token,
            "lastSeen" to System.currentTimeMillis()
        )
        
        firestore.collection("users")
            .document(userId)
            .set(userData)
            .await()
    }
    
    fun getCurrentUserId(): String? = auth.currentUser?.uid
}
