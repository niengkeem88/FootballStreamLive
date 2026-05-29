package keemgames.footballcompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_matches")
data class FavoriteMatchEntity(
    @PrimaryKey
    val matchId: String,
    val title: String,
    val competition: String,
    val date: String,
    val thumbnail: String,
    val matchViewUrl: String
)
