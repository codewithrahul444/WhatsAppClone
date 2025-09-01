package com.example.whatsappclone.di

import android.content.Context
import androidx.room.Room
import com.example.whatsappclone.data.database.ChatDao
import com.example.whatsappclone.data.database.MessageDao
import com.example.whatsappclone.data.database.WhatsAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideWhatsAppDatabase(@ApplicationContext context: Context): WhatsAppDatabase {
        return Room.databaseBuilder(
            context,
            WhatsAppDatabase::class.java,
            "whatsapp_database"
        ).build()
    }
    
    @Provides
    fun provideChatDao(database: WhatsAppDatabase): ChatDao {
        return database.chatDao()
    }
    
    @Provides
    fun provideMessageDao(database: WhatsAppDatabase): MessageDao {
        return database.messageDao()
    }
}
