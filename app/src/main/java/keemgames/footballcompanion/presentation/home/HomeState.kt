package keemgames.footballcompanion.presentation.home

import keemgames.footballcompanion.domain.model.Match
import keemgames.footballcompanion.domain.model.MatchCategory

data class HomeState(
    val isLoading: Boolean = false,
    val allMatches: List<Match> = emptyList(),
    val error: String? = null,
    val selectedTab: Int = 0, // 0=Live, 1=Upcoming, 2=Completed
    val searchQuery: String = "",
    val dateFilter: DateFilterOption = DateFilterOption.ALL
) {
    /** Applies search query against team names and competition */
    private fun List<Match>.filterBySearch(query: String): List<Match> {
        if (query.isBlank()) return this
        val q = query.lowercase()
        return filter { m ->
            m.homeTeam.lowercase().contains(q) ||
            m.awayTeam.lowercase().contains(q) ||
            m.competition.lowercase().contains(q) ||
            m.title.lowercase().contains(q)
        }
    }

    /** Applies date range filter */
    private fun List<Match>.filterByDate(filter: DateFilterOption): List<Match> {
        if (filter == DateFilterOption.ALL) return this
        val now = java.time.LocalDate.now()
        val cutoff = when (filter) {
            DateFilterOption.TODAY -> now
            DateFilterOption.THIS_WEEK -> now.minusDays(7)
            DateFilterOption.THIS_MONTH -> now.minusDays(30)
            else -> return this
        }
        return filter { m ->
            try {
                val matchDate = java.time.LocalDate.parse(m.date, java.time.format.DateTimeFormatter.ISO_LOCAL_DATE)
                !matchDate.isBefore(cutoff)
            } catch (_: Exception) {
                true // include matches with unparseable dates
            }
        }
    }

    val liveMatches: List<Match>
        get() = allMatches.filter { it.category == MatchCategory.LIVE }
            .filterBySearch(searchQuery)
            .filterByDate(dateFilter)

    val upcomingMatches: List<Match>
        get() = allMatches.filter { it.category == MatchCategory.UPCOMING }
            .filterBySearch(searchQuery)
            .filterByDate(dateFilter)

    val completedMatches: List<Match>
        get() = allMatches.filter { it.category == MatchCategory.COMPLETED }
            .filterBySearch(searchQuery)
            .filterByDate(dateFilter)

    val groupedLive: Map<String, List<Match>>
        get() = liveMatches.groupBy { it.competition }

    val groupedUpcoming: Map<String, List<Match>>
        get() = upcomingMatches.groupBy { it.competition }

    val groupedCompleted: Map<String, List<Match>>
        get() = completedMatches.groupBy { it.competition }
}

enum class DateFilterOption(val label: String) {
    ALL("All"),
    TODAY("Today"),
    THIS_WEEK("Week"),
    THIS_MONTH("Month")
}
