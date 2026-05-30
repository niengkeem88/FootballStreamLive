package keemgames.footballcompanion.presentation.home

import keemgames.footballcompanion.domain.model.Match
import keemgames.footballcompanion.domain.model.MatchCategory

data class HomeState(
    val isLoading: Boolean = false,
    val allMatches: List<Match> = emptyList(),
    val error: String? = null,
    val selectedTab: Int = 0 // 0=Live, 1=Upcoming, 2=Completed
) {
    val liveMatches: List<Match>
        get() = allMatches.filter { it.category == MatchCategory.LIVE }

    val upcomingMatches: List<Match>
        get() = allMatches.filter { it.category == MatchCategory.UPCOMING }

    val completedMatches: List<Match>
        get() = allMatches.filter { it.category == MatchCategory.COMPLETED }

    val groupedLive: Map<String, List<Match>>
        get() = liveMatches.groupBy { it.competition }

    val groupedUpcoming: Map<String, List<Match>>
        get() = upcomingMatches.groupBy { it.competition }

    val groupedCompleted: Map<String, List<Match>>
        get() = completedMatches.groupBy { it.competition }
}
