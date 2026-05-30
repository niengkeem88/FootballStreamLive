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
    val matchViewUrl: String,
    val homeTeam: String = "",
    val awayTeam: String = "",
    val homeTeamBadge: String? = null,
    val awayTeamBadge: String? = null,
    val homeScore: String? = null,
    val awayScore: String? = null,
    val status: String = "",
    val videoUrl: String? = null,
    val leagueBadge: String? = null,
    val idLeague: String = "",
    val homeTeamId: String = "",
    val awayTeamId: String = "",
    val venue: String = ""
)
