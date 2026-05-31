package keemgames.footballcompanion.domain.repository

import keemgames.footballcompanion.domain.model.Match
import keemgames.footballcompanion.domain.model.Team
import keemgames.footballcompanion.domain.util.Resource
import kotlinx.coroutines.flow.Flow

data class StandingEntry(
    val rank: Int,
    val teamName: String,
    val teamBadge: String?,
    val played: Int,
    val wins: Int,
    val draws: Int,
    val losses: Int,
    val goalsFor: Int,
    val goalsAgainst: Int,
    val goalDiff: Int,
    val points: Int,
    val form: String
)

data class PlayerInfo(
    val id: String,
    val name: String,
    val position: String?,
    val nationality: String?,
    val thumb: String?,
    val number: String?
)

interface FootballRepository {
    suspend fun getLiveMatches(): Resource<List<Match>>

    suspend fun getMatchById(matchId: String): Resource<Match?>

    suspend fun getStandings(leagueId: String): Resource<List<StandingEntry>>

    suspend fun getTeamPlayers(teamId: String): Resource<List<PlayerInfo>>

    suspend fun getHeadToHead(homeTeamId: String, awayTeamId: String): Resource<List<Match>>

    fun getFavoriteMatches(): Flow<List<Match>>

    suspend fun toggleFavoriteMatch(match: Match)

    fun getFavoriteTeams(): Flow<List<Team>>

    suspend fun toggleFavoriteTeam(team: Team)

    fun isMatchFavorite(matchId: String): Flow<Boolean>
}
