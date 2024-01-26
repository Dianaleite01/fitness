// UserService.kt
package pt.ipt.dam2023.fitness

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {
    @GET("user")
    fun getUsers(): Call<UserResponse>

    @POST("user")
    fun createUser(@Body newUser: UserRequest): Call<UserRequest>

    @POST("calendario")
    fun id(@Body newCalendario: Calendario): Call<Calendario>

    @PUT("user/{iduser}")
    fun updateUser(@Path("iduser") userId: String, @Body userRequest: UserRequest): Call<UserRequest>

}


