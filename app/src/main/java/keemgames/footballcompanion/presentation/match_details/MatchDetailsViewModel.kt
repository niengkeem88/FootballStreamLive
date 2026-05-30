package keemgames.footballcompanion.presentation.match_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import keemgames.footballcompanion.domain.use_case.GetMatchByIdUseCase
import keemgames.footballcompanion.domain.repository.FootballRepository
import keemgames.footballcompanion.domain.util.Resource
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MatchDetailsViewModel @Inject constructor(
    private val getMatchByIdUseCase: GetMatchByIdUseCase,
    private val footballRepository: FootballRepository,
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
                    val match = result.data
                    _state.value = _state.value.copy(match = match)
                    match?.let { m ->
                        if (m.idLeague.isNotBlank()) loadStandings(m.idLeague)
                        if (m.homeTeamId.isNotBlank() || m.awayTeamId.isNotBlank()) loadPlayers(m)
                    }
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun loadStandings(leagueId: String) {
        _state.value = _state.value.copy(standingsLoading = true)
        footballRepository.getStandings(leagueId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(standings = result.data ?: emptyList(), standingsLoading = false)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(standingsLoading = false)
                }
                is Resource.Loading -> { /* already set */ }
            }
        }.launchIn(viewModelScope)
    }

    private fun loadPlayers(match: keemgames.footballcompanion.domain.model.Match) {
        _state.value = _state.value.copy(playersLoading = true)

        if (match.homeTeamId.isNotBlank()) {
            footballRepository.getTeamPlayers(match.homeTeamId).onEach { result ->
                if (result is Resource.Success) {
                    _state.value = _state.value.copy(homePlayers = result.data ?: emptyList())
                }
            }.launchIn(viewModelScope)
        }

        if (match.awayTeamId.isNotBlank()) {
            footballRepository.getTeamPlayers(match.awayTeamId).onEach { result ->
                if (result is Resource.Success) {
                    _state.value = _state.value.copy(
                        awayPlayers = result.data ?: emptyList(),
                        playersLoading = false
                    )
                }
            }.launchIn(viewModelScope)
        }
    }

    fun selectTab(index: Int) {
        _state.value = _state.value.copy(selectedTab = index)
    }
}
