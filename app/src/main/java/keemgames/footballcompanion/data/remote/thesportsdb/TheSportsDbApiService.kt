package keemgames.footballcompanion.data.remote.thesportsdb

import retrofit2.http.GET
import retrofit2.http.Query

interface TheSportsDbApiService {

    @GET("eventsround.php")
    suspend fun getEventsByRound(
        @Query("id") leagueId: String,
        @Query("r") round: String,
        @Query("s") season: String
    ): TheSportsDbEventsResponse

    @GET("eventsseason.php")
    suspend fun getLatestEvents(
        @Query("id") leagueId: String,
        @Query("s") season: String
    ): TheSportsDbEventsResponse

    @GET("lookupevent.php")
    suspend fun getEventById(
        @Query("id") eventId: String
    ): TheSportsDbEventsResponse

    @GET("eventsnext.php")
    suspend fun getNextEventsByTeam(
        @Query("id") teamId: String
    ): TheSportsDbEventsResponse

    @GET("eventslast.php")
    suspend fun getLastEventsByTeam(
        @Query("id") teamId: String
    ): TheSportsDbLastEventsResponse

    @GET("lookupleague.php")
    suspend fun getLeagueById(
        @Query("id") leagueId: String
    ): TheSportsDbEventsResponse

    @GET("lookuptable.php")
    suspend fun getStandings(
        @Query("l") leagueId: String,
        @Query("s") season: String = CURRENT_SEASON
    ): TheSportsDbStandingsResponse

    @GET("lookup_all_players.php")
    suspend fun getTeamPlayers(
        @Query("id") teamId: String
    ): TheSportsDbPlayersResponse

    @GET("lookuptopscorers.php")
    suspend fun getTopScorers(
        @Query("l") leagueId: String
    ): TheSportsDbTopScorersResponse

    companion object {
        const val BASE_URL = "https://www.thesportsdb.com/api/v1/json/3/"
        const val DEFAULT_API_KEY = "3"

        val MAJOR_LEAGUES = mapOf(
            "4328" to "English Premier League",
            "4331" to "German Bundesliga",
            "4332" to "Italian Serie A",
            "4334" to "French Ligue 1",
            "4335" to "Spanish La Liga",
            "4337" to "Dutch Eredivisie",
            "4330" to "Scottish Premier League"
        )

        const val CURRENT_SEASON = "2025-2026"
    }
}
