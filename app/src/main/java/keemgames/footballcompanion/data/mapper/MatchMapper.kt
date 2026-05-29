package keemgames.footballcompanion.data.mapper

import keemgames.footballcompanion.data.local.entity.FavoriteMatchEntity
import keemgames.footballcompanion.data.remote.dto.MatchDto
import keemgames.footballcompanion.domain.model.Match
import keemgames.footballcompanion.domain.model.VideoHighlight

fun MatchDto.toMatch(): Match {
    return Match(
        id = matchViewUrl ?: "",
        title = title ?: "Unknown Match",
        competition = competition ?: "Unknown Competition",
        date = date ?: "",
        thumbnail = thumbnail ?: "",
        matchViewUrl = matchViewUrl ?: "",
        highlights = videos?.map { 
            VideoHighlight(
                title = it.title ?: "Highlight",
                embedHtml = it.embed ?: ""
            )
        } ?: emptyList()
    )
}

fun Match.toFavoriteEntity(): FavoriteMatchEntity {
    return FavoriteMatchEntity(
        matchId = id,
        title = title,
        competition = competition,
        date = date,
        thumbnail = thumbnail,
        matchViewUrl = matchViewUrl
    )
}

fun FavoriteMatchEntity.toMatch(): Match {
    return Match(
        id = matchId,
        title = title,
        competition = competition,
        date = date,
        thumbnail = thumbnail,
        matchViewUrl = matchViewUrl,
        isFavorite = true
    )
}
