package keemgames.footballcompanion.domain.use_case

import keemgames.footballcompanion.domain.model.Match
import keemgames.footballcompanion.domain.repository.FootballRepository
import keemgames.footballcompanion.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLiveMatchesUseCase @Inject constructor(
    private val repository: FootballRepository
) {
    operator fun invoke(): Flow<Resource<List<Match>>> {
        return repository.getLiveMatches()
    }
}
