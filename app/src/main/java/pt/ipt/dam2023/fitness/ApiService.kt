// ApiService.kt
package pt.ipt.dam2023.fitness

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {
    private val gson: Gson = GsonBuilder().setLenient().create()
    private val host = "https://api.sheety.co/2509510ee785287f76db1797fe2632c8/api/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(host)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun service(): UserService = retrofit.create(UserService::class.java)
}


