package luky.zadanie.zadaniefinal.network

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