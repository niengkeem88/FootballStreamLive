package keemgames.footballcompanion.data.local.dao

import androidx.room.*
import keemgames.footballcompanion.data.local.entity.FavoriteMatchEntity
import keemgames.footballcompanion.data.local.entity.FavoriteTeamEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    // Team Favorites
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteTeam(team: FavoriteTeamEntity)

    @Delete
    suspend fun deleteFavoriteTeam(team: FavoriteTeamEntity)

    @Query("SELECT * FROM favorite_teams")
    fun getAllFavoriteTeams(): Flow<List<FavoriteTeamEntity>>

    // Match Favorites
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMatch(match: FavoriteMatchEntity)

    @Delete
    suspend fun deleteFavoriteMatch(match: FavoriteMatchEntity)

    @Query("SELECT * FROM favorite_matches")
    fun getAllFavoriteMatches(): Flow<List<FavoriteMatchEntity>>
    
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_matches WHERE matchId = :matchId)")
    fun isMatchFavorite(matchId: String): Flow<Boolean>
}
