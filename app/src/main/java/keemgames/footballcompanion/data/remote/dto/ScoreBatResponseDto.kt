package keemgames.footballcompanion.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ScoreBatResponseDto(
    @SerializedName("response")
    val response: List<MatchDto>? = null
)

data class MatchDto(
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("competition")
    val competition: String? = null,
    @SerializedName("matchviewUrl")
    val matchViewUrl: String? = null,
    @SerializedName("competitionUrl")
    val competitionUrl: String? = null,
    @SerializedName("thumbnail")
    val thumbnail: String? = null,
    @SerializedName("date")
    val date: String? = null,
    @SerializedName("videos")
    val videos: List<VideoDto>? = null
)

data class VideoDto(
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("embed")
    val embed: String? = null
)
