package keemgames.footballcompanion.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import keemgames.footballcompanion.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    init {
        combine(
            preferencesRepository.themeModeFlow,
            preferencesRepository.onboardingCompletedFlow
        ) { theme, onboarding ->
            SettingsState(theme, onboarding)
        }.onEach { newState ->
            _state.value = newState
        }.launchIn(viewModelScope)
    }

    fun onThemeChange(mode: String) {
        viewModelScope.launch {
            preferencesRepository.updateThemeMode(mode)
        }
    }
}
