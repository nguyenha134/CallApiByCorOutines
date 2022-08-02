package com.example.callapicoroutines.network

import com.example.callapicoroutines.constant.Constants
import com.example.callapicoroutines.model.MovieItem
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetroService {

    @GET("marvel")
    suspend fun getAllMovies(): Response<List<MovieItem>>

    companion object {
        private var retrofitService: RetroService? = null

        fun getInstance(): RetroService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetroService::class.java)
            }
            return retrofitService!!
        }
    }
}