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
    var lon: Double,
    @SerializedName("users")
    var usersCount: Int
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

