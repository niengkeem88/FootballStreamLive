package keemgames.footballcompanion.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import keemgames.footballcompanion.data.local.dao.FavoritesDao
import keemgames.footballcompanion.data.local.entity.FavoriteMatchEntity
import keemgames.footballcompanion.data.local.entity.FavoriteTeamEntity

@Database(
    entities = [FavoriteTeamEntity::class, FavoriteMatchEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val favoritesDao: FavoritesDao

    companion object {
        const val DATABASE_NAME = "football_companion_db"
    }
}
