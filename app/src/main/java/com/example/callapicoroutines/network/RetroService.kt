package com.example.testcallapibycoroutines.network

import com.example.callapicoroutines.model.MovieItem
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetroService {

    @GET("marvel")
    suspend fun getAllMovies(): Response<List<MovieItem>>

    companion object {
        var retrofitService: RetroService? = null
        fun getInstance(): RetroService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://simplifiedcoding.net/demos/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetroService::class.java)
            }
            return retrofitService!!
        }
    }
}