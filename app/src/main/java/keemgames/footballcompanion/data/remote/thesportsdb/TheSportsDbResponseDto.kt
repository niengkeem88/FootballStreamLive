package keemgames.footballcompanion.data.remote.thesportsdb

import com.google.gson.annotations.SerializedName

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
