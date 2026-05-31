package keemgames.footballcompanion.presentation.match_details

import keemgames.footballcompanion.domain.repository.PlayerInfo
import keemgames.footballcompanion.domain.repository.StandingEntry
import keemgames.footballcompanion.domain.model.Match

data class MatchDetailsState(
    val isLoading: Boolean = false,
    val match: Match? = null,
    val error: String? = null,
    val selectedTab: Int = 0,
    val standings: List<StandingEntry> = emptyList(),
    val homePlayers: List<PlayerInfo> = emptyList(),
    val awayPlayers: List<PlayerInfo> = emptyList(),
    val standingsLoading: Boolean = false,
    val playersLoading: Boolean = false,
    val headToHead: List<Match> = emptyList(),
    val headToHeadLoading: Boolean = false
)
