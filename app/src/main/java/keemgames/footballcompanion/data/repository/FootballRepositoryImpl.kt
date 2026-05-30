package keemgames.footballcompanion.data.repository

import keemgames.footballcompanion.data.local.dao.FavoritesDao
import keemgames.footballcompanion.data.mapper.toFavoriteEntity
import keemgames.footballcompanion.data.mapper.toMatch as scoreBatToMatch
import keemgames.footballcompanion.data.mapper.toEntity
import keemgames.footballcompanion.data.mapper.toTeam
import keemgames.footballcompanion.data.mapper.toMatch as theSportsDbToMatch
import keemgames.footballcompanion.data.remote.thesportsdb.TheSportsDbApiService
import keemgames.footballcompanion.domain.model.Match
import keemgames.footballcompanion.domain.model.Team
import keemgames.footballcompanion.domain.repository.FootballRepository
import keemgames.footballcompanion.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

class FootballRepositoryImpl @Inject constructor(
    private val dao: FavoritesDao,
    private val theSportsDbApi: TheSportsDbApiService
) : FootballRepository {

    override fun getLiveMatches(): Flow<Resource<List<Match>>> = flow {
        emit(Resource.Loading())

        try {
            val matches = mutableListOf<Match>()

            // Fetch events from all major leagues concurrently
            supervisorScope {
                val deferredResults = TheSportsDbApiService.MAJOR_LEAGUES.keys.map { leagueId ->
                    async(Dispatchers.IO) {
                        try {
                            val response = theSportsDbApi.getLatestEvents(
                                leagueId = leagueId,
                                season = TheSportsDbApiService.CURRENT_SEASON
                            )
                            response.events?.map { it.theSportsDbToMatch() } ?: emptyList()
                        } catch (e: Exception) {
                            emptyList<Match>()
                        }
                    }
                }

                deferredResults.forEach { deferred ->
                    matches.addAll(deferred.await())
                }
            }

            // Sort by date (most recent first)
            matches.sortByDescending { it.date }

            emit(Resource.Success(matches))
        } catch (e: Exception) {
            emit(Resource.Error("Oops, something went wrong: ${e.message}"))
        }
    }

    override fun getMatchById(matchId: String): Flow<Resource<Match?>> = flow {
        emit(Resource.Loading())

        try {
            val response = theSportsDbApi.getEventById(matchId)
            val match = response.events?.firstOrNull()?.theSportsDbToMatch()
            emit(Resource.Success(match))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to load match: ${e.message}"))
        }
    }

    override fun getFavoriteMatches(): Flow<List<Match>> {
        return dao.getAllFavoriteMatches().map { entities ->
            entities.map { it.scoreBatToMatch() }
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
