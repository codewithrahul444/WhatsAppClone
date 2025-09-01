package com.example.whatsappclone.utils

object Constants {
    // Firebase Collections
    const val USERS_COLLECTION = "users"
    const val CHATS_COLLECTION = "chats"
    const val MESSAGES_COLLECTION = "messages"
    
    // Notification
    const val NOTIFICATION_CHANNEL_ID = "whatsapp_messages"
    const val NOTIFICATION_CHANNEL_NAME = "Messages"
    
    // SharedPreferences
    const val PREFS_NAME = "whatsapp_prefs"
    const val KEY_USER_ID = "user_id"
    const val KEY_USER_NAME = "user_name"
    const val KEY_USER_EMAIL = "user_email"
    const val KEY_FCM_TOKEN = "fcm_token"
    
    // Intent Extras
    const val EXTRA_CHAT_ID = "chat_id"
    const val EXTRA_USER_ID = "user_id"
    const val EXTRA_USER_NAME = "user_name"
    
    // Message Types
    const val MESSAGE_TYPE_TEXT = "text"
    const val MESSAGE_TYPE_IMAGE = "image"
    const val MESSAGE_TYPE_AUDIO = "audio"
    const val MESSAGE_TYPE_VIDEO = "video"
    const val MESSAGE_TYPE_DOCUMENT = "document"
    
    // Message Status
    const val MESSAGE_STATUS_SENT = "sent"
    const val MESSAGE_STATUS_DELIVERED = "delivered"
    const val MESSAGE_STATUS_READ = "read"
    
    // User Status
    const val USER_STATUS_ONLINE = "online"
    const val USER_STATUS_OFFLINE = "offline"
    const val USER_STATUS_TYPING = "typing"
    
    // Limits
    const val MAX_MESSAGE_LENGTH = 4096
    const val MAX_USERNAME_LENGTH = 25
    const val MAX_STATUS_LENGTH = 139
    
    // Timeouts
    const val TYPING_TIMEOUT = 3000L
    const val ONLINE_TIMEOUT = 30000L
}
