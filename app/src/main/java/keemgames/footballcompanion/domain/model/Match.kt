package keemgames.footballcompanion.domain.model

data class Match(
    val id: String,
    val title: String,
    val competition: String,
    val date: String,
    val thumbnail: String,
    val matchViewUrl: String,
    val highlights: List<VideoHighlight> = emptyList(),
    val isFavorite: Boolean = false
)
