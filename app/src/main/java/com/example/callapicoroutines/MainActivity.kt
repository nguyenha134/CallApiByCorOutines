package com.example.callapicoroutines

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.callapicoroutines.adapter.MovieAdapter
import com.example.callapicoroutines.databinding.ActivityMainBinding
import com.example.callapicoroutines.repository.MainRepository
import com.example.callapicoroutines.viewModel.MovieViewModel
import com.example.callapicoroutines.viewModel.MyViewModelFactory
import com.example.callapicoroutines.network.RetroService

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieViewModel
    private val adapter = MovieAdapter()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initViewModel()
        initData()
        observeData()
        onRefreshData()
    }

    private fun onRefreshData() {
        binding.srlLayout.setOnRefreshListener {
            binding.srlLayout.isRefreshing = false
        }
    }

    private fun initView() {
        binding.recyclerview.adapter = adapter
    }

    private fun initViewModel() {
        val retrofitService = RetroService.getInstance()
        val mainRepository = MainRepository(retrofitService)

        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(mainRepository)
        )[MovieViewModel::class.java]
    }

    private fun observeData() {
        viewModel.movieList.observe(this) {
            adapter.setMovies(it)
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressbar.visibility = View.GONE
            } else {
                binding.progressbar.visibility = View.VISIBLE
            }
        })
    }

    private fun initData() {
        viewModel.getAllMovies()
    }
}