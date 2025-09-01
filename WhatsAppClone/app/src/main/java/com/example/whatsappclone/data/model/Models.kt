package com.example.whatsappclone.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity(tableName = "chats")
data class Chat(
    @PrimaryKey val id: String,
    val name: String,
    val profileImage: String? = null,
    val lastMessage: String = "",
    val lastMessageTime: LocalDateTime? = null,
    val unreadCount: Int = 0
)

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey val id: String,
    val chatId: String,
    val content: String,
    val timestamp: LocalDateTime,
    val isFromMe: Boolean,
    val messageType: MessageType = MessageType.TEXT
)

enum class MessageType {
    TEXT, IMAGE, AUDIO, VIDEO
}

data class ChatWithMessages(
    val chat: Chat,
    val messages: List<Message>
)
