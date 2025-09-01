# Firebase Setup Guide

## Steps to setup Firebase:

### 1. Firebase Console Setup
1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create new project "WhatsAppClone"
3. Enable Google Analytics (optional)

### 2. Add Android App
1. Click "Add app" → Android
2. Package name: `com.example.whatsappclone`
3. Download `google-services.json`
4. Place file in `app/` directory

### 3. Enable Services
1. **Authentication**:
   - Go to Authentication → Sign-in method
   - Enable "Anonymous" authentication

2. **Firestore Database**:
   - Go to Firestore Database → Create database
   - Start in "test mode" for development
   - Choose location closest to users

3. **Cloud Messaging**:
   - Go to Cloud Messaging
   - No additional setup needed for basic notifications

### 4. Firestore Security Rules (Development)
```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

### 5. Data Structure
```
chats/
  {chatId}/
    - name: string
    - participants: array
    - lastMessage: string
    - lastMessageTime: timestamp
    - unreadCount: number
    
    messages/
      {messageId}/
        - content: string
        - senderId: string
        - timestamp: timestamp

users/
  {userId}/
    - fcmToken: string
    - lastSeen: timestamp

notifications/
  {notificationId}/
    - title: string
    - body: string
    - chatId: string
    - timestamp: timestamp
```

### 6. Push Notification Setup

#### For Testing (Firebase Console):
1. Go to Cloud Messaging → Send your first message
2. Enter notification title and text
3. Select your app
4. Send test message

#### For Production (Backend Required):
- Use Firebase Admin SDK on your backend
- Send notifications when messages are received
- Include custom data payload with chatId

### 7. Build & Run
1. Place `google-services.json` in `app/` folder
2. Sync project
3. Run app
4. App will auto sign-in anonymously
5. Grant notification permission when prompted
6. Use "+" button to create sample chats

## Features Included:
- ✅ Real-time messaging
- ✅ Anonymous authentication
- ✅ Auto-sync across devices
- ✅ Offline support
- ✅ Message ordering
- ✅ Loading states
- ✅ Push notifications
- ✅ FCM token management
- ✅ Notification tap handling
- ✅ Background notifications

## Testing Notifications:
1. Install app on 2 devices/emulators
2. Create a chat between them
3. Send message from one device
4. Other device will receive push notification
5. Tap notification to open specific chat

## Production Notes:
- For production, implement proper backend server
- Use Firebase Admin SDK to send notifications
- Add user authentication (phone/email)
- Implement proper security rules
- Add notification sound and vibration
- Handle notification permissions properly
