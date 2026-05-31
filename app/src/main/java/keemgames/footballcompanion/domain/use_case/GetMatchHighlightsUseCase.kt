package keemgames.footballcompanion.domain.use_case

import keemgames.footballcompanion.domain.model.Match
import keemgames.footballcompanion.domain.repository.FootballRepository
import keemgames.footballcompanion.domain.util.Resource
import javax.inject.Inject

/**
 * Fetches match highlights from TheSportsDB via the repository.
 */
class GetMatchHighlightsUseCase @Inject constructor(
    private val repository: FootballRepository
) {
    suspend operator fun invoke(matchUrl: String): Resource<Match?> {
        return repository.getMatchById(matchUrl)
    }
}
