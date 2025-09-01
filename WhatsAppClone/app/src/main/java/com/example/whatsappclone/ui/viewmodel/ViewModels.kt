package com.example.whatsappclone.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsappclone.data.model.Chat
import com.example.whatsappclone.data.model.Message
import com.example.whatsappclone.data.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {
    
    private val _chats = MutableStateFlow<List<Chat>>(emptyList())
    val chats: StateFlow<List<Chat>> = _chats.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    init {
        signInAndLoadChats()
    }
    
    private fun signInAndLoadChats() {
        viewModelScope.launch {
            _isLoading.value = true
            
            // Sign in anonymously if not signed in
            if (firebaseRepository.getCurrentUserId() == null) {
                firebaseRepository.signInAnonymously()
            }
            
            // Initialize user with FCM token
            firebaseRepository.initializeUser()
            
            // Load chats
            firebaseRepository.getChatsFlow().collect {
                _chats.value = it
                _isLoading.value = false
            }
        }
    }
    
    fun createSampleChat() {
        viewModelScope.launch {
            try {
                firebaseRepository.createChat("sample_user", "Sample Chat")
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {
    
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    fun loadMessages(chatId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            firebaseRepository.getMessagesFlow(chatId).collect {
                _messages.value = it
                _isLoading.value = false
            }
        }
    }
    
    fun sendMessage(chatId: String, content: String) {
        viewModelScope.launch {
            try {
                firebaseRepository.sendMessage(chatId, content)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
