package keemgames.footballcompanion.data.repository

import keemgames.footballcompanion.data.local.dao.FavoritesDao
import keemgames.footballcompanion.data.mapper.toMatch
import keemgames.footballcompanion.data.mapper.toFavoriteEntity
import keemgames.footballcompanion.data.mapper.toTeam
import keemgames.footballcompanion.data.mapper.toEntity
import keemgames.footballcompanion.domain.model.Match
import keemgames.footballcompanion.domain.model.Team
import keemgames.footballcompanion.domain.repository.FootballRepository
import keemgames.footballcompanion.domain.util.Resource
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class FootballRepositoryImpl @Inject constructor(
    private val dao: FavoritesDao
) : FootballRepository {

    override fun getLiveMatches(): Flow<Resource<List<Match>>> = flow {
        emit(Resource.Loading())
        
        try {
            // Live matches are now provided by the API-Football widget on the UI side
            // This method returns an empty success to maintain backward compatibility
            emit(Resource.Success(emptyList()))
        } catch (e: Exception) {
            emit(Resource.Error("Oops, something went wrong: ${e.message}"))
        }
    }

    override fun getFavoriteMatches(): Flow<List<Match>> {
        return dao.getAllFavoriteMatches().map { entities ->
            entities.map { it.toMatch() }
        }
    }

    override suspend fun toggleFavoriteMatch(match: Match) {
        val isFavorite = dao.isMatchFavorite(match.id).first()
        if (isFavorite) {
            dao.deleteFavoriteMatch(match.toFavoriteEntity())
        } else {
            dao.insertFavoriteMatch(match.toFavoriteEntity())
        }
    }

    override fun getFavoriteTeams(): Flow<List<Team>> {
        return dao.getAllFavoriteTeams().map { entities ->
            entities.map { it.toTeam() }
        }
    }

    override suspend fun toggleFavoriteTeam(team: Team) {
        val existingTeams = dao.getAllFavoriteTeams().first()
        val isFav = existingTeams.any { it.id == team.id }
        if (isFav) {
            dao.deleteFavoriteTeam(team.toEntity())
        } else {
            dao.insertFavoriteTeam(team.toEntity())
        }
    }

    override fun isMatchFavorite(matchId: String): Flow<Boolean> {
        return dao.isMatchFavorite(matchId)
    }
}
