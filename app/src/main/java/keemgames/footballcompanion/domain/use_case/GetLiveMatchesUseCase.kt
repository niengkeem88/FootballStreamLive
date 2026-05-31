package keemgames.footballcompanion.domain.use_case

import keemgames.footballcompanion.domain.model.Match
import keemgames.footballcompanion.domain.repository.FootballRepository
import keemgames.footballcompanion.domain.util.Resource
import javax.inject.Inject

class GetLiveMatchesUseCase @Inject constructor(
    private val repository: FootballRepository
) {
    suspend operator fun invoke(): Resource<List<Match>> {
        return repository.getLiveMatches()
    }
}
