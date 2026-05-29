package keemgames.footballcompanion.domain.use_case

import keemgames.footballcompanion.domain.model.Team
import keemgames.footballcompanion.domain.repository.FootballRepository
import javax.inject.Inject

class ToggleFavoriteTeamUseCase @Inject constructor(
    private val repository: FootballRepository
) {
    suspend operator fun invoke(team: Team) {
        repository.toggleFavoriteTeam(team)
    }
}
