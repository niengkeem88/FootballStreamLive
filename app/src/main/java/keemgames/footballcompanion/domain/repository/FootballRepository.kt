package keemgames.footballcompanion.domain.repository

import keemgames.footballcompanion.domain.model.Match
import keemgames.footballcompanion.domain.model.Team
import keemgames.footballcompanion.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface FootballRepository {
    fun getLiveMatches(): Flow<Resource<List<Match>>>
    
    fun getFavoriteMatches(): Flow<List<Match>>
    
    suspend fun toggleFavoriteMatch(match: Match)
    
    fun getFavoriteTeams(): Flow<List<Team>>
    
    suspend fun toggleFavoriteTeam(team: Team)
    
    fun isMatchFavorite(matchId: String): Flow<Boolean>
}
