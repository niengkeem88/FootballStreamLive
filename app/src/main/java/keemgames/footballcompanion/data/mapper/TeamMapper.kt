package keemgames.footballcompanion.data.mapper

import keemgames.footballcompanion.data.local.entity.FavoriteTeamEntity
import keemgames.footballcompanion.domain.model.Team

fun FavoriteTeamEntity.toTeam(): Team {
    return Team(
        id = id,
        name = name,
        logoUrl = logoUrl,
        competition = competition,
        isFavorite = true
    )
}

fun Team.toEntity(): FavoriteTeamEntity {
    return FavoriteTeamEntity(
        id = id,
        name = name,
        logoUrl = logoUrl,
        competition = competition
    )
}
