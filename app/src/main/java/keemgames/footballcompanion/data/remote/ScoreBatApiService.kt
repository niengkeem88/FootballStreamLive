package keemgames.footballcompanion.data.remote

import keemgames.footballcompanion.data.remote.dto.ScoreBatResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ScoreBatApiService {

    @GET("video-api/v3/free-feed/")
    suspend fun getVideoFeed(
        @Query("token") token: String
    ): ScoreBatResponseDto

    companion object {
        const val BASE_URL = "https://www.scorebat.com/"
    }
}
