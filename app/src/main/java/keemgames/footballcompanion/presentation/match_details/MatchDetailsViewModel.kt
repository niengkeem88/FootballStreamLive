package keemgames.footballcompanion.presentation.match_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import keemgames.footballcompanion.domain.use_case.GetMatchByIdUseCase
import keemgames.footballcompanion.domain.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MatchDetailsViewModel @Inject constructor(
    private val getMatchByIdUseCase: GetMatchByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(MatchDetailsState())
    val state: StateFlow<MatchDetailsState> = _state.asStateFlow()

    init {
        savedStateHandle.get<String>("matchUrl")?.let { eventId ->
            getMatchDetails(eventId)
        }
    }

    private fun getMatchDetails(eventId: String) {
        getMatchByIdUseCase(eventId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = MatchDetailsState(match = result.data)
                }
                is Resource.Error -> {
                    _state.value = MatchDetailsState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = MatchDetailsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
