package keemgames.footballcompanion.presentation.match_details

import keemgames.footballcompanion.domain.model.Match

data class MatchDetailsState(
    val isLoading: Boolean = false,
    val match: Match? = null,
    val error: String? = null
)
