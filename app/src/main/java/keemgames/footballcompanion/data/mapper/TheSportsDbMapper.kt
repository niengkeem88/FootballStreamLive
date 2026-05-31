package keemgames.footballcompanion.data.mapper

import keemgames.footballcompanion.data.remote.thesportsdb.TheSportsDbEventDto
import keemgames.footballcompanion.domain.model.Match
import keemgames.footballcompanion.domain.model.VideoHighlight

fun TheSportsDbEventDto.toMatch(): Match {
    return Match(
        id = idEvent ?: "",
        idLeague = idLeague ?: "",
        homeTeamId = idHomeTeam ?: "",
        awayTeamId = idAwayTeam ?: "",
        venue = strVenue ?: "",
        matchTime = strTime ?: "",
        title = strEvent ?: "${strHomeTeam ?: "?"} vs ${strAwayTeam ?: "?"}",
        competition = strLeague ?: "Unknown League",
        date = dateEvent ?: "",
        thumbnail = strThumb ?: strPoster ?: strSquare ?: "",
        matchViewUrl = "",
        highlights = if (!strVideo.isNullOrBlank()) {
            listOf(
                VideoHighlight(
                    title = "Match Highlights",
                    embedHtml = buildYouTubeEmbed(strVideo)
                )
            )
        } else emptyList(),
        homeTeam = strHomeTeam ?: "",
        awayTeam = strAwayTeam ?: "",
        homeTeamBadge = strHomeTeamBadge,
        awayTeamBadge = strAwayTeamBadge,
        homeScore = intHomeScore,
        awayScore = intAwayScore,
        status = resolveStatus(strStatus, strPostponed),
        videoUrl = strVideo,
        leagueBadge = strLeagueBadge
    )
}

fun TheSportsDbTopScorerDto.toTopScorer(): keemgames.footballcompanion.domain.model.TopScorer {
    return keemgames.footballcompanion.domain.model.TopScorer(
        id = idPlayer ?: "",
        name = strPlayer ?: "Unknown",
        teamName = strTeam ?: "",
        teamBadge = strBadge,
        position = strPosition,
        nationality = strNationality,
        thumb = strThumb,
        number = strNumber,
        goalCount = intGoals?.toIntOrNull() ?: 0,
        rank = intRank?.toIntOrNull() ?: 0
    )
}

private fun resolveStatus(status: String?, postponed: String?): String {
    if (postponed == "yes") return "Postponed"
    return when (status?.uppercase()) {
        "FT" -> "Full Time"
        "HT" -> "Half Time"
        "NS" -> "Not Started"
        "1H" -> "First Half"
        "2H" -> "Second Half"
        "ET" -> "Extra Time"
        "P" -> "Penalties"
        "ABD" -> "Abandoned"
        "INT" -> "Interrupted"
        "AET" -> "After Extra Time"
        "CAN" -> "Cancelled"
        "SUSP" -> "Suspended"
        "AWARDED" -> "Awarded"
        "WO" -> "Walkover"
        "LIVE" -> "Live"
        else -> status ?: ""
    }
}

private fun buildYouTubeEmbed(videoUrl: String): String {
    // Extract YouTube video ID from various URL formats
    val videoId = when {
        videoUrl.contains("youtube.com/watch?v=") -> {
            videoUrl.substringAfter("v=").substringBefore("&")
        }
        videoUrl.contains("youtu.be/") -> {
            videoUrl.substringAfter("youtu.be/").substringBefore("?")
        }
        videoUrl.contains("youtube.com/embed/") -> {
            videoUrl.substringAfter("embed/").substringBefore("?")
        }
        else -> return ""
    }

    return """
        <iframe 
            width="100%" 
            height="100%" 
            src="https://www.youtube.com/embed/$videoId" 
            frameborder="0" 
            allowfullscreen="true"
            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture">
        </iframe>
    """.trimIndent()
}
