package com.example.callapicoroutines

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.callapicoroutines.adapter.MovieAdapter
import com.example.callapicoroutines.databinding.ActivityMainBinding
import com.example.callapicoroutines.repo.MainRepository
import com.example.callapicoroutines.viewModel.MovieViewModel
import com.example.callapicoroutines.viewModel.MyViewModelFactory
import com.example.testcallapibycoroutines.network.RetroService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MovieViewModel
    private val adapter = MovieAdapter()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofitService = RetroService.getInstance()
        val mainRepository = MainRepository(retrofitService)
        binding.recyclerview.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(mainRepository)
        )[MovieViewModel::class.java]


        viewModel.movieList.observe(this) {
            adapter.setMovies(it)
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }


//        viewModel.loading.observe(this, Observer {
//            if (it) {
//                binding.progressDialog.visibility = View.VISIBLE
//            } else {
//                binding.progressDialog.visibility = View.GONE
//            }
//        })

        viewModel.getAllMovies()

    }
}