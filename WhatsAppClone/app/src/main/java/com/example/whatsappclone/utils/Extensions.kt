package com.example.whatsappclone.utils

import java.text.SimpleDateFormat
import java.util.*

fun Long.toTimeString(): String {
    val now = System.currentTimeMillis()
    val diff = now - this
    
    return when {
        diff < 60_000 -> "अभी"
        diff < 3600_000 -> "${diff / 60_000} मिनट पहले"
        diff < 86400_000 -> {
            val format = SimpleDateFormat("HH:mm", Locale.getDefault())
            format.format(Date(this))
        }
        diff < 604800_000 -> {
            val format = SimpleDateFormat("EEE", Locale.getDefault())
            format.format(Date(this))
        }
        else -> {
            val format = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
            format.format(Date(this))
        }
    }
}

fun Long.toChatTimeString(): String {
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.format(Date(this))
}

fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPhone(): Boolean {
    return android.util.Patterns.PHONE.matcher(this).matches()
}

fun generateChatId(userId1: String, userId2: String): String {
    return if (userId1 < userId2) {
        "${userId1}_${userId2}"
    } else {
        "${userId2}_${userId1}"
    }
}
