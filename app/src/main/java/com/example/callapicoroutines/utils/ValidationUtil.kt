package com.example.callapicoroutines.utils

import com.example.callapicoroutines.model.MovieItem

object ValidationUtil {

    fun validateMovie(movie: MovieItem) : Boolean {
        if (movie.name.isNotEmpty()) {
            return true
        }
        return false
    }

}