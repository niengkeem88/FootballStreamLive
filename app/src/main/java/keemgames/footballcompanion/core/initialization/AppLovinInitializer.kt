package keemgames.footballcompanion.core.initialization

import android.content.Context
import com.applovin.sdk.AppLovinMediationProvider
import com.applovin.sdk.AppLovinSdk
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
class AppLovinInitializer @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val _isSdkInitialized = MutableStateFlow(false)
    val isSdkInitialized: StateFlow<Boolean> = _isSdkInitialized.asStateFlow()

    fun initialize() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val sdk = AppLovinSdk.getInstance(context)
                sdk.mediationProvider = AppLovinMediationProvider.MAX
                sdk.initializeSdk {
                    _isSdkInitialized.value = true
                }
            } catch (e: Exception) {
                // Safeguard against crash if SDK key is missing or invalid
                e.printStackTrace()
            }
        }
    }
}
