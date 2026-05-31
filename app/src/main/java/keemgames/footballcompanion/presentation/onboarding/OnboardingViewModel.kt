package keemgames.footballcompanion.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import keemgames.footballcompanion.domain.repository.PreferencesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val repository: PreferencesRepository
) : ViewModel() {

    fun completeOnboarding(onComplete: () -> Unit) {
        // Onboarding is shown on every app launch.
        // Just navigate to home without persisting the completed flag.
        onComplete()
    }
}
