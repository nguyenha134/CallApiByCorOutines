package com.example.callapicoroutines.repo

import com.example.callapicoroutines.model.MovieItem
import com.example.callapicoroutines.network.NetworkState
import com.example.testcallapibycoroutines.network.RetroService

class MainRepository constructor(private val retrofitService: RetroService) {

    suspend fun getAllMovies(): NetworkState<List<MovieItem>> {
        val response = retrofitService.getAllMovies()
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkState.Success(responseBody)
            } else {
                NetworkState.Error(response)
            }
        } else {
            NetworkState.Error(response)
        }
    }
}