package keemgames.footballcompanion.presentation.favorites

import keemgames.footballcompanion.domain.model.Match
import keemgames.footballcompanion.domain.model.Team

data class FavoritesState(
    val favoriteTeams: List<Team> = emptyList(),
    val favoriteMatches: List<Match> = emptyList()
)
