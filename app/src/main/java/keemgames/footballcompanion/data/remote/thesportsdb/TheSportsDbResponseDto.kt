package keemgames.footballcompanion.data.remote.thesportsdb

import com.google.gson.annotations.SerializedName

// -- Events --

data class TheSportsDbEventsResponse(
    @SerializedName("events")
    val events: List<TheSportsDbEventDto>? = null
)

data class TheSportsDbEventDto(
    @SerializedName("idEvent")
    val idEvent: String? = null,
    @SerializedName("strEvent")
    val strEvent: String? = null,
    @SerializedName("strEventAlternate")
    val strEventAlternate: String? = null,
    @SerializedName("strFilename")
    val strFilename: String? = null,
    @SerializedName("strSport")
    val strSport: String? = null,
    @SerializedName("idLeague")
    val idLeague: String? = null,
    @SerializedName("strLeague")
    val strLeague: String? = null,
    @SerializedName("strLeagueBadge")
    val strLeagueBadge: String? = null,
    @SerializedName("strSeason")
    val strSeason: String? = null,
    @SerializedName("strHomeTeam")
    val strHomeTeam: String? = null,
    @SerializedName("strAwayTeam")
    val strAwayTeam: String? = null,
    @SerializedName("intHomeScore")
    val intHomeScore: String? = null,
    @SerializedName("intAwayScore")
    val intAwayScore: String? = null,
    @SerializedName("intRound")
    val intRound: String? = null,
    @SerializedName("strStatus")
    val strStatus: String? = null,
    @SerializedName("strPostponed")
    val strPostponed: String? = null,
    @SerializedName("dateEvent")
    val dateEvent: String? = null,
    @SerializedName("dateEventLocal")
    val dateEventLocal: String? = null,
    @SerializedName("strTime")
    val strTime: String? = null,
    @SerializedName("strTimeLocal")
    val strTimeLocal: String? = null,
    @SerializedName("strVenue")
    val strVenue: String? = null,
    @SerializedName("strCity")
    val strCity: String? = null,
    @SerializedName("strCountry")
    val strCountry: String? = null,
    @SerializedName("strThumb")
    val strThumb: String? = null,
    @SerializedName("strPoster")
    val strPoster: String? = null,
    @SerializedName("strSquare")
    val strSquare: String? = null,
    @SerializedName("strBanner")
    val strBanner: String? = null,
    @SerializedName("strVideo")
    val strVideo: String? = null,
    @SerializedName("strHomeTeamBadge")
    val strHomeTeamBadge: String? = null,
    @SerializedName("strAwayTeamBadge")
    val strAwayTeamBadge: String? = null,
    @SerializedName("idHomeTeam")
    val idHomeTeam: String? = null,
    @SerializedName("idAwayTeam")
    val idAwayTeam: String? = null,
    @SerializedName("strOfficial")
    val strOfficial: String? = null,
    @SerializedName("strDescriptionEN")
    val strDescriptionEN: String? = null,
    @SerializedName("strTimestamp")
    val strTimestamp: String? = null,
    @SerializedName("strResult")
    val strResult: String? = null
)

// -- Standings --

data class TheSportsDbStandingsResponse(
    @SerializedName("table")
    val table: List<StandingEntryDto>? = null
)

data class StandingEntryDto(
    @SerializedName("idTeam")
    val idTeam: String? = null,
    @SerializedName("strTeam")
    val strTeam: String? = null,
    @SerializedName("strBadge")
    val strBadge: String? = null,
    @SerializedName("intPlayed")
    val intPlayed: String? = null,
    @SerializedName("intWin")
    val intWin: String? = null,
    @SerializedName("intDraw")
    val intDraw: String? = null,
    @SerializedName("intLoss")
    val intLoss: String? = null,
    @SerializedName("intPoints")
    val intPoints: String? = null,
    @SerializedName("intGoalsFor")
    val intGoalsFor: String? = null,
    @SerializedName("intGoalsAgainst")
    val intGoalsAgainst: String? = null,
    @SerializedName("intGoalDifference")
    val intGoalDifference: String? = null,
    @SerializedName("strForm")
    val strForm: String? = null,
    @SerializedName("strDescription")
    val strDescription: String? = null,
    @SerializedName("intRank")
    val intRank: String? = null
)


// -- Last Events (for head-to-head) --

data class TheSportsDbLastEventsResponse(
    @SerializedName("results")
    val results: List<TheSportsDbEventDto>? = null
)

// -- Top Scorers --

data class TheSportsDbTopScorersResponse(
    @SerializedName("topscorers")
    val topScorers: List<TheSportsDbTopScorerDto>? = null
)

data class TheSportsDbTopScorerDto(
    @SerializedName("idPlayer")
    val idPlayer: String? = null,
    @SerializedName("strPlayer")
    val strPlayer: String? = null,
    @SerializedName("strTeam")
    val strTeam: String? = null,
    @SerializedName("strBadge")
    val strBadge: String? = null,
    @SerializedName("strPosition")
    val strPosition: String? = null,
    @SerializedName("strNationality")
    val strNationality: String? = null,
    @SerializedName("strThumb")
    val strThumb: String? = null,
    @SerializedName("intGoals")
    val intGoals: String? = null,
    @SerializedName("strNumber")
    val strNumber: String? = null,
    @SerializedName("intRank")
    val intRank: String? = null
)

// -- League (with multi-language descriptions) --

data class TheSportsDbLeagueResponse(
    @SerializedName("leagues")
    val leagues: List<TheSportsDbLeagueDto>? = null
)

data class TheSportsDbLeagueDto(
    @SerializedName("idLeague")
    val idLeague: String? = null,
    @SerializedName("strLeague")
    val strLeague: String? = null,
    @SerializedName("strLeagueAlternate")
    val strLeagueAlternate: String? = null,
    @SerializedName("strBadge")
    val strBadge: String? = null,
    @SerializedName("strLogo")
    val strLogo: String? = null,
    @SerializedName("strTrophy")
    val strTrophy: String? = null,
    @SerializedName("strDescriptionEN")
    val strDescriptionEN: String? = null,
    @SerializedName("strDescriptionDE")
    val strDescriptionDE: String? = null,
    @SerializedName("strDescriptionFR")
    val strDescriptionFR: String? = null,
    @SerializedName("strDescriptionIT")
    val strDescriptionIT: String? = null,
    @SerializedName("strDescriptionES")
    val strDescriptionES: String? = null,
    @SerializedName("strDescriptionPT")
    val strDescriptionPT: String? = null,
    @SerializedName("strDescriptionNL")
    val strDescriptionNL: String? = null,
    @SerializedName("strDescriptionRU")
    val strDescriptionRU: String? = null,
    @SerializedName("strDescriptionJP")
    val strDescriptionJP: String? = null,
    @SerializedName("strDescriptionCN")
    val strDescriptionCN: String? = null,
    @SerializedName("strDescriptionNO")
    val strDescriptionNO: String? = null,
    @SerializedName("strDescriptionSE")
    val strDescriptionSE: String? = null,
    @SerializedName("strDescriptionPL")
    val strDescriptionPL: String? = null,
    @SerializedName("strCountry")
    val strCountry: String? = null,
    @SerializedName("intFormedYear")
    val intFormedYear: String? = null,
    @SerializedName("strWebsite")
    val strWebsite: String? = null
) {
    /** Returns the description in the requested language, falling back to EN */
    fun getDescription(langSuffix: String): String? {
        return when (langSuffix) {
            "EN" -> strDescriptionEN; "FR" -> strDescriptionFR; "DE" -> strDescriptionDE
            "IT" -> strDescriptionIT; "ES" -> strDescriptionES; "PT" -> strDescriptionPT
            "NL" -> strDescriptionNL; "RU" -> strDescriptionRU; "JP" -> strDescriptionJP
            "CN" -> strDescriptionCN; "NO" -> strDescriptionNO; "SE" -> strDescriptionSE
            "PL" -> strDescriptionPL
            else -> strDescriptionEN
        } ?: strDescriptionEN
    }
}

// -- Team (with multi-language descriptions) --

data class TheSportsDbTeamResponse(
    @SerializedName("teams")
    val teams: List<TheSportsDbTeamDto>? = null
)

data class TheSportsDbTeamDto(
    @SerializedName("idTeam")
    val idTeam: String? = null,
    @SerializedName("strTeam")
    val strTeam: String? = null,
    @SerializedName("strTeamShort")
    val strTeamShort: String? = null,
    @SerializedName("strAlternate")
    val strAlternate: String? = null,
    @SerializedName("strBadge")
    val strBadge: String? = null,
    @SerializedName("strLogo")
    val strLogo: String? = null,
    @SerializedName("strBanner")
    val strBanner: String? = null,
    @SerializedName("strDescriptionEN")
    val strDescriptionEN: String? = null,
    @SerializedName("strDescriptionDE")
    val strDescriptionDE: String? = null,
    @SerializedName("strDescriptionFR")
    val strDescriptionFR: String? = null,
    @SerializedName("strDescriptionIT")
    val strDescriptionIT: String? = null,
    @SerializedName("strDescriptionES")
    val strDescriptionES: String? = null,
    @SerializedName("strDescriptionPT")
    val strDescriptionPT: String? = null,
    @SerializedName("strDescriptionNL")
    val strDescriptionNL: String? = null,
    @SerializedName("strDescriptionRU")
    val strDescriptionRU: String? = null,
    @SerializedName("strDescriptionJP")
    val strDescriptionJP: String? = null,
    @SerializedName("strDescriptionCN")
    val strDescriptionCN: String? = null,
    @SerializedName("strDescriptionNO")
    val strDescriptionNO: String? = null,
    @SerializedName("strDescriptionSE")
    val strDescriptionSE: String? = null,
    @SerializedName("strDescriptionPL")
    val strDescriptionPL: String? = null,
    @SerializedName("strCountry")
    val strCountry: String? = null,
    @SerializedName("intFormedYear")
    val intFormedYear: String? = null,
    @SerializedName("strStadium")
    val strStadium: String? = null,
    @SerializedName("strStadiumThumb")
    val strStadiumThumb: String? = null,
    @SerializedName("strWebsite")
    val strWebsite: String? = null
) {
    /** Returns the description in the requested language, falling back to EN */
    fun getDescription(langSuffix: String): String? {
        return when (langSuffix) {
            "EN" -> strDescriptionEN; "FR" -> strDescriptionFR; "DE" -> strDescriptionDE
            "IT" -> strDescriptionIT; "ES" -> strDescriptionES; "PT" -> strDescriptionPT
            "NL" -> strDescriptionNL; "RU" -> strDescriptionRU; "JP" -> strDescriptionJP
            "CN" -> strDescriptionCN; "NO" -> strDescriptionNO; "SE" -> strDescriptionSE
            "PL" -> strDescriptionPL
            else -> strDescriptionEN
        } ?: strDescriptionEN
    }
}

// -- Players --

data class TheSportsDbPlayersResponse(
    @SerializedName("player")
    val player: List<PlayerDto>? = null
)

data class PlayerDto(
    @SerializedName("idPlayer")
    val idPlayer: String? = null,
    @SerializedName("strPlayer")
    val strPlayer: String? = null,
    @SerializedName("strPosition")
    val strPosition: String? = null,
    @SerializedName("strNationality")
    val strNationality: String? = null,
    @SerializedName("strThumb")
    val strThumb: String? = null,
    @SerializedName("strNumber")
    val strNumber: String? = null,
    @SerializedName("dateBorn")
    val dateBorn: String? = null,
    @SerializedName("strSigning")
    val strSigning: String? = null
)
