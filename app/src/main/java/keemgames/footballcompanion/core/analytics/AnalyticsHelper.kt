package keemgames.footballcompanion.core.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface AnalyticsHelper {
    fun logEvent(name: String, params: Map<String, Any>? = null)
    fun logVideoPlay(matchTitle: String)
    fun logFavoriteTeamAdded(teamName: String)
}

@Singleton
class AnalyticsHelperImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AnalyticsHelper {

    private val firebaseAnalytics by lazy {
        try {
            FirebaseAnalytics.getInstance(context)
        } catch (e: Exception) {
            null
        }
    }

    override fun logEvent(name: String, params: Map<String, Any>?) {
        val bundle = Bundle()
        params?.forEach { (key, value) ->
            when (value) {
                is String -> bundle.putString(key, value)
                is Int -> bundle.putInt(key, value)
                is Long -> bundle.putLong(key, value)
                is Double -> bundle.putDouble(key, value)
                is Boolean -> bundle.putBoolean(key, value)
            }
        }
        firebaseAnalytics?.logEvent(name, bundle)
    }

    override fun logVideoPlay(matchTitle: String) {
        logEvent("video_play", mapOf("match_title" to matchTitle))
    }

    override fun logFavoriteTeamAdded(teamName: String) {
        logEvent("add_favorite_team", mapOf("team_name" to teamName))
    }
}
