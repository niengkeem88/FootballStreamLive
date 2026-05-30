package keemgames.footballcompanion

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import keemgames.footballcompanion.core.initialization.AdMobInitializer
import keemgames.footballcompanion.core.notifications.NotificationManagerHelper
import javax.inject.Inject

@HiltAndroidApp
class FootballCompanionApp : Application() {

    @Inject
    lateinit var adMobInitializer: AdMobInitializer

    @Inject
    lateinit var notificationHelper: NotificationManagerHelper

    override fun onCreate() {
        super.onCreate()
        try {
            adMobInitializer.initialize()
            notificationHelper.createNotificationChannels()
        } catch (e: Exception) {
            // Defensive catch to ensure the app boots even if core services fail
            e.printStackTrace()
        }
    }
}
