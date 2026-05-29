package keemgames.footballcompanion.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import keemgames.footballcompanion.domain.repository.PreferencesRepository
import keemgames.footballcompanion.presentation.navigation.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: PreferencesRepository
) : ViewModel() {

    private val _navigationEvent = MutableSharedFlow<String>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        checkOnboardingStatus()
    }

    private fun checkOnboardingStatus() {
        viewModelScope.launch {
            val onboardingCompleted = repository.onboardingCompletedFlow.first()
            if (onboardingCompleted) {
                _navigationEvent.emit(Screen.Home.route)
            } else {
                _navigationEvent.emit(Screen.Onboarding.route)
            }
        }
    }
}
