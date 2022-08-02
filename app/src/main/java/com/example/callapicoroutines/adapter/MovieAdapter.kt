package com.example.callapicoroutines.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.callapicoroutines.utils.ValidationUtil
import com.example.callapicoroutines.databinding.ItemMoviesBinding
import com.example.callapicoroutines.model.MovieItem

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MainViewHolder>() {

    private val movieList = mutableListOf<MovieItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMoviesBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val movie = movieList[position]
        if (ValidationUtil.validateMovie(movie)) {
            holder.binding.tvNameMovie.text = movie.name
            holder.binding.tvCategory.text = movie.createdby
            holder.binding.tvFirstAppearance.text = movie.firstappearance
            holder.binding.tvPublisher.text = movie.publisher
            holder.binding.tvBio.text = movie.bio
            Glide.with(holder.itemView.context).load(movie.imageurl).into(holder.binding.imgMovie)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    class MainViewHolder(val binding: ItemMoviesBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMovies(movies: List<MovieItem>) {
        movieList.clear()
        movieList.addAll(movies)
        notifyDataSetChanged()
    }
}