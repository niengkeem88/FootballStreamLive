package keemgames.footballcompanion.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import keemgames.footballcompanion.domain.use_case.GetFavoriteTeamsUseCase
import keemgames.footballcompanion.domain.use_case.ToggleFavoriteTeamUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteTeamsUseCase: GetFavoriteTeamsUseCase,
    private val toggleFavoriteTeamUseCase: ToggleFavoriteTeamUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FavoritesState())
    val state: StateFlow<FavoritesState> = _state.asStateFlow()

    init {
        getFavoriteTeamsUseCase().onEach { teams ->
            _state.value = _state.value.copy(favoriteTeams = teams)
        }.launchIn(viewModelScope)
    }

    fun onToggleFavoriteTeam(team: keemgames.footballcompanion.domain.model.Team) {
        viewModelScope.launch {
            toggleFavoriteTeamUseCase(team)
        }
    }
}
