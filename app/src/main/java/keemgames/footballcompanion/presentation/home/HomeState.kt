package keemgames.footballcompanion.presentation.home

import keemgames.footballcompanion.domain.model.Match

data class HomeState(
    val isLoading: Boolean = false,
    val matches: List<Match> = emptyList(),
    val error: String? = null
)
