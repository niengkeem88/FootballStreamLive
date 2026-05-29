package keemgames.footballcompanion.domain.use_case

import keemgames.footballcompanion.domain.model.Team
import keemgames.footballcompanion.domain.repository.FootballRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteTeamsUseCase @Inject constructor(
    private val repository: FootballRepository
) {
    operator fun invoke(): Flow<List<Team>> {
        return repository.getFavoriteTeams()
    }
}
