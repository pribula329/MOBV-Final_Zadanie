package luky.zadanie.zadaniefinal.network

import com.google.gson.annotations.SerializedName

data class PubListData(
    @SerializedName("bar_id")
    val pubId: String,
    @SerializedName("bar_name")
    val pubName: String,
    @SerializedName("bar_type")
    val pubType: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("users")
    val usersCount: Int
)


data class PubDetailData(
    val id: String,
    val tags: Tags
)

data class Tags(
    var email: String? = null,
    var phone: String? = null,
    var website: String? = null
)

data class PubDetailResponse(
    val elements: List<PubDetailData>
)

data class PubNearData(
    val id: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("tags")
    val tagsNear: TagsNear
)

data class TagsNear(
    @SerializedName("amenity")
    val pubNearType: String,
    @SerializedName("name")
    val pubNearName: String

)

data class PubNearResponse(
    val elements: List<PubNearData>
)
