package keemgames.footballcompanion.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import keemgames.footballcompanion.domain.use_case.GetLiveMatchesUseCase
import keemgames.footballcompanion.domain.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLiveMatchesUseCase: GetLiveMatchesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        // Disabled JSON API fetch to avoid 403 errors; relying on the Widget instead.
        // getMatches() 
    }

    fun getMatches() {
        // Intentionally left empty as we are moving to Widget-only view
    }
}
