package keemgames.footballcompanion.domain.model

data class Team(
    val id: String,
    val name: String,
    val logoUrl: String,
    val competition: String,
    val isFavorite: Boolean = false
)
