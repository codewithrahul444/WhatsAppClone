package com.example.whatsappclone.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.whatsappclone.ui.screens.ChatListScreen
import com.example.whatsappclone.ui.screens.ChatScreen

@Composable
fun WhatsAppNavigation(
    navController: NavHostController = rememberNavController(),
    initialChatId: String? = null
) {
    // Navigate to specific chat if opened from notification
    LaunchedEffect(initialChatId) {
        initialChatId?.let { chatId ->
            navController.navigate("chat/$chatId")
        }
    }
    
    NavHost(
        navController = navController,
        startDestination = "chat_list"
    ) {
        composable("chat_list") {
            ChatListScreen(
                onChatClick = { chatId ->
                    navController.navigate("chat/$chatId")
                }
            )
        }
        
        composable("chat/{chatId}") { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
            ChatScreen(
                chatId = chatId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
