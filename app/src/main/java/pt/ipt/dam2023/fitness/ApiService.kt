// ApiService.kt
package pt.ipt.dam2023.fitness

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {
    private val gson: Gson = GsonBuilder().setLenient().create()
    private val host = "https://api.sheety.co/907d82c7c1e2fffda1a10ef3aae90792/api/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(host)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun service(): UserService = retrofit.create(UserService::class.java)
}


