package luky.zadanie.zadaniefinal.network

import com.google.gson.annotations.SerializedName

data class UserRequestData(
    val name: String,
    val password: String
)

data class UserRefreshData(
    val refresh: String
)

data class UserResponseData(
    val uid: String,
    val access: String,
    val refresh: String
)

data class AddDeleteFriendData(
    val contact: String
)

data class FriendData(
    @SerializedName("user_id")
    val idFriend: String,
    @SerializedName("user_name")
    val nameFriend: String
)

data class FriendWithPubData(
    @SerializedName("user_id")
    val idFriend: String,
    @SerializedName("user_name")
    val nameFriend: String,
    @SerializedName("bar_id")
    val idPubFriend: String?,
    @SerializedName("bar_name")
    val namePubFriend: String?,
    @SerializedName("bar_lat")
    val latPubFriend: String?,
    @SerializedName("bar_lon")
    val lonPubFriend: String?,
)