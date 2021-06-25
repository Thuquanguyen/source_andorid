package checkvn.com.viettiepbhdt.presentation.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.presentation.ui.splash.SplashActivity
import checkvn.com.viettiepbhdt.utils.Constants
import checkvn.com.viettiepbhdt.utils.Constants.CHANNEL_ID
import checkvn.com.viettiepbhdt.utils.Constants.CHANNEL_NAME
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.pixplicity.easyprefs.library.Prefs


class FcmService : FirebaseMessagingService() {

    private var numberOfBadge = 0

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        showNotification(message)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        handleNewToken()
    }

    private fun handleNewToken() {
        //Send new token to server.
    }

    private fun showNotification(message: RemoteMessage) {
        val intent = Intent(this, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val notificationTitle = message.notification?.title
        val notificationContent = message.notification?.body

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle(notificationTitle)
            .setPriority(NotificationManager.IMPORTANCE_MAX)
            .setContentText(notificationContent)
            .setFullScreenIntent(pendingIntent, true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setAutoCancel(true)
            .setNumber(numberOfBadge++)

        if (Prefs.getBoolean(Constants.SOUND, true)) {
            builder.setDefaults(Notification.DEFAULT_SOUND)
        }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Notification Channel is required for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.setShowBadge(true)
            channel.canShowBadge()
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, builder.build())
    }

}

