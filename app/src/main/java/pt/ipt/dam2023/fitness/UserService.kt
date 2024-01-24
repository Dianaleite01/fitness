// UserService.kt
package pt.ipt.dam2023.fitness

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body

interface UserService {
    @GET("user")
    fun getUsers(): Call<UserResponse>

    @POST("user")
    fun createUser(@Body newUser: UserRequest): Call<UserRequest>
}
