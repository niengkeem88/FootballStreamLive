package keemgames.footballcompanion.data.mapper

import keemgames.footballcompanion.data.local.entity.FavoriteMatchEntity
import keemgames.footballcompanion.domain.model.Match
import keemgames.footballcompanion.domain.model.VideoHighlight

fun Match.toFavoriteEntity(): FavoriteMatchEntity {
    return FavoriteMatchEntity(
        matchId = id,
        title = title,
        competition = competition,
        date = date,
        thumbnail = thumbnail,
        matchViewUrl = matchViewUrl,
        homeTeam = homeTeam,
        awayTeam = awayTeam,
        homeTeamBadge = homeTeamBadge,
        awayTeamBadge = awayTeamBadge,
        homeScore = homeScore,
        awayScore = awayScore,
        status = status,
        videoUrl = videoUrl,
        leagueBadge = leagueBadge
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
        isFavorite = true,
        homeTeam = homeTeam,
        awayTeam = awayTeam,
        homeTeamBadge = homeTeamBadge,
        awayTeamBadge = awayTeamBadge,
        homeScore = homeScore,
        awayScore = awayScore,
        status = status,
        videoUrl = videoUrl,
        leagueBadge = leagueBadge
    )
}
