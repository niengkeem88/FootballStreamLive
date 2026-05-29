package keemgames.footballcompanion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import keemgames.footballcompanion.domain.repository.PreferencesRepository
import keemgames.footballcompanion.presentation.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _startDestination = MutableStateFlow<String?>(null)
    val startDestination: StateFlow<String?> = _startDestination.asStateFlow()

    init {
        preferencesRepository.onboardingCompletedFlow.onEach { completed ->
            if (completed) {
                _startDestination.value = Screen.Home.route
            } else {
                _startDestination.value = Screen.Onboarding.route
            }
        }.launchIn(viewModelScope)
    }
}
