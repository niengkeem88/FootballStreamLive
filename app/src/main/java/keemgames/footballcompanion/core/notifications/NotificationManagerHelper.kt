package keemgames.footballcompanion.core.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import keemgames.footballcompanion.MainActivity
import keemgames.footballcompanion.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationManagerHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val notificationManager = 
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object {
        const val CHANNEL_LIVE_UPDATES = "live_match_updates"
        const val CHANNEL_HIGHLIGHTS = "daily_highlights"
    }

    fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val liveChannel = NotificationChannel(
                CHANNEL_LIVE_UPDATES,
                "Live Match Updates",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Get real-time updates for live matches"
            }

            val highlightChannel = NotificationChannel(
                CHANNEL_HIGHLIGHTS,
                "Daily Highlights",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Daily recap of the best goals and moments"
            }

            notificationManager.createNotificationChannel(liveChannel)
            notificationManager.createNotificationChannel(highlightChannel)
        }
    }

    fun showMatchNotification(title: String, body: String, matchUrl: String) {
        // Deep link intent - this would be handled by SetupNavGraph
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            // We pass the route that SetupNavGraph expects
            putExtra("route", "match_details/${java.net.URLEncoder.encode(matchUrl, "UTF-8")}")
        }

        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_LIVE_UPDATES)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Use foreground as fallback
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(matchUrl.hashCode(), notification)
    }
}
