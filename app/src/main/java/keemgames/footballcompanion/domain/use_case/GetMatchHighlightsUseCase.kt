package keemgames.footballcompanion.domain.use_case

import keemgames.footballcompanion.domain.model.Match
import keemgames.footballcompanion.domain.repository.FootballRepository
import keemgames.footballcompanion.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * In ScoreBat v3 Free Feed, highlights are part of the match object.
 * This use case fetches the live feed and filters for a specific match to get its highlights.
 */
class GetMatchHighlightsUseCase @Inject constructor(
    private val repository: FootballRepository
) {
    operator fun invoke(matchUrl: String): Flow<Resource<Match?>> {
        return repository.getLiveMatches().map { resource ->
            when (resource) {
                is Resource.Success -> {
                    val match = resource.data?.find { it.matchViewUrl == matchUrl }
                    Resource.Success(match)
                }
                is Resource.Error -> Resource.Error(resource.message ?: "Unknown Error")
                is Resource.Loading -> Resource.Loading()
            }
        }
    }
}
