package com.example.callapicoroutines.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.callapicoroutines.repository.MainRepository
import com.example.callapicoroutines.model.MovieItem
import com.example.callapicoroutines.network.NetworkState
import kotlinx.coroutines.*

class MovieViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    val movieList = MutableLiveData<List<MovieItem>>()
    val loading = MutableLiveData<Boolean>()

    fun getAllMovies() {
        Log.d("Thread Outside", Thread.currentThread().name)
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("Thread Inside", Thread.currentThread().name)
            loading.value = true
            val response = mainRepository.getAllMovies()
            loading.value = false
            when (response) {
                is NetworkState.Success -> {
                    withContext(Dispatchers.Main) {
                        movieList.value = (response.data)
                    }
                }
                is NetworkState.Error -> {
                    when (response.response.code()) {
                        401 -> {
                            _errorMessage.value = "Loi 401"
                        }
                        403 -> {
                            _errorMessage.value = "Loi 403"
                        }
                        500 -> {
                            _errorMessage.value = "Loi server"
                        }
                        else -> {
                            _errorMessage.value = "Loi khong xac dinh"
                        }
                    }
                }
            }
        }
    }
}

