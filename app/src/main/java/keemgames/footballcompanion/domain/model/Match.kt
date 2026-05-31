package keemgames.footballcompanion.domain.model

enum class MatchCategory {
    LIVE,
    UPCOMING,
    COMPLETED
}

data class Match(
    val id: String,
    val title: String = "",
    val competition: String,
    val date: String = "",
    val thumbnail: String = "",
    val matchViewUrl: String = "",
    val highlights: List<VideoHighlight> = emptyList(),
    val isFavorite: Boolean = false,
    // TheSportsDB enriched fields
    val homeTeam: String = "",
    val awayTeam: String = "",
    val homeTeamBadge: String? = null,
    val awayTeamBadge: String? = null,
    val homeScore: String? = null,
    val awayScore: String? = null,
    val status: String = "",
    val videoUrl: String? = null,
    val leagueBadge: String? = null,
    val idLeague: String = "",
    val homeTeamId: String = "",
    val awayTeamId: String = "",
    val venue: String = "",
    val matchTime: String = "" // e.g. "19:00:00" from strTime
) {
    val category: MatchCategory
        get() = when (status.uppercase()) {
            "LIVE", "1H", "FIRST HALF", "2H", "SECOND HALF", "HT", "HALF TIME", "ET", "EXTRA TIME", "P", "PENALTIES", "INT", "INTERRUPTED", "SUSP", "SUSPENDED" -> MatchCategory.LIVE
            "NS", "NOT STARTED" -> MatchCategory.UPCOMING
            else -> MatchCategory.COMPLETED
        }
}

/**
 * Represents a top scorer or player stat entry for a league.
 * goalCount may be 0 when using the test API key which doesn't include
 * individual goal data — full stats require a premium TheSportsDB key.
 */
data class TopScorer(
    val id: String,
    val name: String,
    val teamName: String,
    val teamBadge: String? = null,
    val position: String? = null,
    val nationality: String? = null,
    val thumb: String? = null,
    val number: String? = null,
    val goalCount: Int = 0,
    val rank: Int = 0
)
