// User.kt
package pt.ipt.dam2023.fitness

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: String,
    @SerializedName("email") val email: String,
    @SerializedName("nome") val nome: String,
    @SerializedName("password") val password: String,
    @SerializedName("ftoperfil") val ftperfil: String,
    @SerializedName("peso") val peso: String,
    @SerializedName("altura") val altura: String,
    @SerializedName("codGym") val codGym: Int

)

data class UserResponse(
    @SerializedName("user") val users: List<User>?
)

data class UserRequest(
    @SerializedName("user") val user: User?,
)

