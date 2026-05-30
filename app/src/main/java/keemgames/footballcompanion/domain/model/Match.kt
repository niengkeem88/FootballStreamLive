package keemgames.footballcompanion.domain.model

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
    val leagueBadge: String? = null
)
