// User.kt
package pt.ipt.dam2023.fitness

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("iduser") val iduser: String,
    @SerializedName("email") val email: String,
    @SerializedName("nome") val nome: String,
    @SerializedName("password") val password: String,
    @SerializedName("ftoperfil") val ftperfil: String,
    @SerializedName("peso") val peso: String,
    @SerializedName("altura") val altura: String,
    @SerializedName("codGym") val codGym: Int,
    @SerializedName("imc") val imc: String,
    @SerializedName("dieta") val dieta: String,
    @SerializedName("admin") val admin: Boolean,
)

data class Calendario(
    @SerializedName("id") val id: String,
    @SerializedName("atividade") val ativ: String,
    @SerializedName("data") val data: String,
    @SerializedName("calendario") val calen: List<Calendario>?
)

data class UserResponse(
    @SerializedName("user") val users: List<User>?
)

data class UserRequest(
    @SerializedName("user") val user: User?,
)


