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
