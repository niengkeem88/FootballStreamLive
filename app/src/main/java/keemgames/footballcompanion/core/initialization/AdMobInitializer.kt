package keemgames.footballcompanion.core.initialization

import android.content.Context
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdMobInitializer @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean> = _isInitialized.asStateFlow()

    fun initialize() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                MobileAds.initialize(context) { _: InitializationStatus ->
                    _isInitialized.value = true
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
