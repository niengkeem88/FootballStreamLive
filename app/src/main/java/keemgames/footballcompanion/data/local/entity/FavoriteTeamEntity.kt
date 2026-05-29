package keemgames.footballcompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_teams")
data class FavoriteTeamEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val logoUrl: String,
    val competition: String
)
