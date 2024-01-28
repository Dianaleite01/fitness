// ApiService.kt
package pt.ipt.dam2023.fitness

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {
    private val gson: Gson = GsonBuilder().setLenient().create()
    private val host = "https://api.sheety.co/7a6ab3f12df3a64f8ad0b07c1f78d482/dam/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(host)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun service(): UserService = retrofit.create(UserService::class.java)
}


