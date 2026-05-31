package keemgames.footballcompanion.presentation.match_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import keemgames.footballcompanion.domain.use_case.GetMatchByIdUseCase
import keemgames.footballcompanion.domain.repository.FootballRepository
import keemgames.footballcompanion.domain.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            val result = getMatchByIdUseCase(eventId)
            when (result) {
                is Resource.Success -> {
                    val match = result.data
                    _state.value = _state.value.copy(isLoading = false, match = match)
                    match?.let { m ->
                        if (m.idLeague.isNotBlank()) loadStandings(m.idLeague)
                        if (m.homeTeamId.isNotBlank() || m.awayTeamId.isNotBlank()) loadPlayers(m)
                    }
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }
    }

    private fun loadStandings(leagueId: String) {
        _state.value = _state.value.copy(standingsLoading = true)
        viewModelScope.launch {
            val result = footballRepository.getStandings(leagueId)
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(standings = result.data ?: emptyList(), standingsLoading = false)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(standingsLoading = false)
                }
                is Resource.Loading -> { /* no-op */ }
            }
        }
    }

    private fun loadPlayers(match: keemgames.footballcompanion.domain.model.Match) {
        _state.value = _state.value.copy(playersLoading = true)

        viewModelScope.launch {
            var homeSuccess = true
            var awaySuccess = true

            if (match.homeTeamId.isNotBlank()) {
                val homeResult = footballRepository.getTeamPlayers(match.homeTeamId)
                if (homeResult is Resource.Success) {
                    _state.value = _state.value.copy(homePlayers = homeResult.data ?: emptyList())
                } else {
                    homeSuccess = false
                }
            }

            if (match.awayTeamId.isNotBlank()) {
                val awayResult = footballRepository.getTeamPlayers(match.awayTeamId)
                if (awayResult is Resource.Success) {
                    _state.value = _state.value.copy(awayPlayers = awayResult.data ?: emptyList())
                } else {
                    awaySuccess = false
                }
            }

            _state.value = _state.value.copy(playersLoading = false)
        }
    }

    fun selectTab(index: Int) {
        _state.value = _state.value.copy(selectedTab = index)
    }
}
