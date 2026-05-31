package keemgames.footballcompanion.data.repository

import keemgames.footballcompanion.data.local.dao.FavoritesDao
import keemgames.footballcompanion.data.mapper.toFavoriteEntity
import keemgames.footballcompanion.data.mapper.toMatch as favoriteToMatch
import keemgames.footballcompanion.data.mapper.toEntity
import keemgames.footballcompanion.data.mapper.toTeam
import keemgames.footballcompanion.data.mapper.toMatch as theSportsDbToMatch
import keemgames.footballcompanion.data.remote.thesportsdb.TheSportsDbApiService
import keemgames.footballcompanion.domain.model.Match
import keemgames.footballcompanion.domain.model.Team
import keemgames.footballcompanion.domain.model.TopScorer
import keemgames.footballcompanion.domain.repository.FootballRepository
import keemgames.footballcompanion.domain.repository.PlayerInfo
import keemgames.footballcompanion.domain.repository.StandingEntry
import keemgames.footballcompanion.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FootballRepositoryImpl @Inject constructor(
    private val dao: FavoritesDao,
    private val theSportsDbApi: TheSportsDbApiService
) : FootballRepository {

    override suspend fun getLiveMatches(): Resource<List<Match>> = withContext(Dispatchers.IO) {
        try {
            val matches = mutableListOf<Match>()
            for (leagueId in TheSportsDbApiService.MAJOR_LEAGUES.keys) {
                try {
                    val response = theSportsDbApi.getLatestEvents(
                        leagueId = leagueId,
                        season = TheSportsDbApiService.CURRENT_SEASON
                    )
                    response.events?.forEach { event ->
                        matches.add(event.theSportsDbToMatch())
                    }
                } catch (e: Exception) {
                    android.util.Log.w("FootballRepo", "Failed to load league $leagueId: ${e.message}")
                }
            }
            matches.sortByDescending { it.date }
            Resource.Success(matches)
        } catch (e: Exception) {
            Resource.Error("Oops, something went wrong: ${e.message}")
        }
    }

    override suspend fun getMatchById(matchId: String): Resource<Match?> = withContext(Dispatchers.IO) {
        try {
            val response = theSportsDbApi.getEventById(matchId)
            val match = response.events?.firstOrNull()?.theSportsDbToMatch()
            Resource.Success(match)
        } catch (e: Exception) {
            Resource.Error("Failed to load match: ${e.message}")
        }
    }

    override suspend fun getStandings(leagueId: String): Resource<List<StandingEntry>> = withContext(Dispatchers.IO) {
        try {
            val response = theSportsDbApi.getStandings(leagueId)
            val entries = response.table?.mapNotNull { row ->
                row.intPlayed?.toIntOrNull()?.let {
                    StandingEntry(
                        rank = row.intRank?.toIntOrNull() ?: 0,
                        teamName = row.strTeam ?: "Unknown",
                        teamBadge = row.strBadge,
                        played = it,
                        wins = row.intWin?.toIntOrNull() ?: 0,
                        draws = row.intDraw?.toIntOrNull() ?: 0,
                        losses = row.intLoss?.toIntOrNull() ?: 0,
                        goalsFor = row.intGoalsFor?.toIntOrNull() ?: 0,
                        goalsAgainst = row.intGoalsAgainst?.toIntOrNull() ?: 0,
                        goalDiff = row.intGoalDifference?.toIntOrNull() ?: 0,
                        points = row.intPoints?.toIntOrNull() ?: 0,
                        form = row.strForm ?: ""
                    )
                }
            } ?: emptyList()
            Resource.Success(entries)
        } catch (e: Exception) {
            Resource.Error("Failed to load standings: ${e.message}")
        }
    }

    override suspend fun getTeamPlayers(teamId: String): Resource<List<PlayerInfo>> = withContext(Dispatchers.IO) {
        try {
            val response = theSportsDbApi.getTeamPlayers(teamId)
            val players = response.player?.map { p ->
                PlayerInfo(
                    id = p.idPlayer ?: "",
                    name = p.strPlayer ?: "Unknown",
                    position = p.strPosition,
                    nationality = p.strNationality,
                    thumb = p.strThumb,
                    number = p.strNumber
                )
            } ?: emptyList()
            Resource.Success(players)
        } catch (e: Exception) {
            Resource.Error("Failed to load players: ${e.message}")
        }
    }

    override suspend fun getHeadToHead(homeTeamId: String, awayTeamId: String): Resource<List<Match>> = withContext(Dispatchers.IO) {
        try {
            val homeResponse = theSportsDbApi.getLastEventsByTeam(homeTeamId)
            val awayResponse = theSportsDbApi.getLastEventsByTeam(awayTeamId)

            // Collect all events from both teams
            val allEvents = (homeResponse.results.orEmpty() + awayResponse.results.orEmpty())

            // Find head-to-head matches (either direction)
            val h2h = allEvents
                .filter { event ->
                    (event.idHomeTeam == homeTeamId && event.idAwayTeam == awayTeamId) ||
                    (event.idHomeTeam == awayTeamId && event.idAwayTeam == homeTeamId)
                }
                .distinctBy { it.idEvent ?: "" }
                .sortedByDescending { it.dateEvent }
                .map { it.theSportsDbToMatch() }

            Resource.Success(h2h)
        } catch (e: Exception) {
            Resource.Error("Failed to load head-to-head: ${e.message}")
        }
    }

    override suspend fun getTopScorers(leagueId: String): Resource<List<TopScorer>> = withContext(Dispatchers.IO) {
        try {
            // Try the dedicated API endpoint first
            val response = theSportsDbApi.getTopScorers(leagueId)
            val apiScorers = response.topScorers
            if (!apiScorers.isNullOrEmpty()) {
                return@withContext Resource.Success(
                    apiScorers.map { it.toTopScorer() }
                )
            }

            // Fallback: build from standings teams + players
            val standingsResponse = theSportsDbApi.getStandings(leagueId)
            val teams = standingsResponse.table.orEmpty()

            var rank = 0
            val scorers = mutableListOf<TopScorer>()
            for (team in teams) {
                val teamId = team.idTeam ?: continue
                try {
                    val playersResponse = theSportsDbApi.getTeamPlayers(teamId)
                    playersResponse.player?.forEach { p ->
                        rank++
                        scorers.add(
                            TopScorer(
                                id = p.idPlayer ?: "player_$rank",
                                name = p.strPlayer ?: "Unknown",
                                teamName = team.strTeam ?: "",
                                teamBadge = team.strBadge,
                                position = p.strPosition,
                                nationality = p.strNationality,
                                thumb = p.strThumb,
                                number = p.strNumber,
                                goalCount = 0,
                                rank = rank
                            )
                        )
                    }
                } catch (_: Exception) { /* skip team if player fetch fails */ }
            }

            Resource.Success(scorers)
        } catch (e: Exception) {
            Resource.Error("Failed to load top scorers: ${e.message}")
        }
    }

    override fun getFavoriteMatches(): Flow<List<Match>> {
        return dao.getAllFavoriteMatches().map { entities ->
            entities.map { entity -> entity.favoriteToMatch() }
        }
    }

    override suspend fun toggleFavoriteMatch(match: Match) {
        val isFavorite = dao.isMatchFavorite(match.id).first()
        if (isFavorite) dao.deleteFavoriteMatch(match.toFavoriteEntity())
        else dao.insertFavoriteMatch(match.toFavoriteEntity())
    }

    override fun getFavoriteTeams(): Flow<List<Team>> {
        return dao.getAllFavoriteTeams().map { entities -> entities.map { it.toTeam() } }
    }

    override suspend fun toggleFavoriteTeam(team: Team) {
        val existingTeams = dao.getAllFavoriteTeams().first()
        val isFav = existingTeams.any { it.id == team.id }
        if (isFav) dao.deleteFavoriteTeam(team.toEntity())
        else dao.insertFavoriteTeam(team.toEntity())
    }

    override fun isMatchFavorite(matchId: String): Flow<Boolean> {
        return dao.isMatchFavorite(matchId)
    }
}
