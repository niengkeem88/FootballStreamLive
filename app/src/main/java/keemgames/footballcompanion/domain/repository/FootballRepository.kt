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
    fun getLiveMatches(): Flow<Resource<List<Match>>>

    fun getMatchById(matchId: String): Flow<Resource<Match?>>

    fun getStandings(leagueId: String): Flow<Resource<List<StandingEntry>>>

    fun getTeamPlayers(teamId: String): Flow<Resource<List<PlayerInfo>>>

    fun getFavoriteMatches(): Flow<List<Match>>

    suspend fun toggleFavoriteMatch(match: Match)

    fun getFavoriteTeams(): Flow<List<Team>>

    suspend fun toggleFavoriteTeam(team: Team)

    fun isMatchFavorite(matchId: String): Flow<Boolean>
}
