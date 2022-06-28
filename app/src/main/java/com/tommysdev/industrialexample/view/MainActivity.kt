package com.tommysdev.industrialexample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tommysdev.industrialexample.databinding.ActivityMainBinding
import com.tommysdev.industrialexample.viewModel.PopulationByYearViewModel
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val countriesAdapter = CountryPopulationAdapter(arrayListOf())
    private val viewModel: PopulationByYearViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.fetchPopulationByYear()

        binding.countriesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        //lifecycleScope ties MainActivity or Fragment Life Cycle with the coroutine. This Coroutine will be active as long the activity is active.
        //If I use launchWhenStarted the updating the UI part will only take place after my application comes to foreground again and my activity comes to  start a
        //place of its lifecycle (this won't cause a crash).
        // lifecycleScope.launchWhenResumed {  } is another option.
        lifecycleScope.launchWhenStarted {
            viewModel.populationByYears.collect {
                binding.countriesList.visibility = View.VISIBLE
                countriesAdapter.updateCountries(it)
            }
        }

        //To listen to listen to loadError , I use collect instead of observe
        lifecycleScope.launchWhenResumed {
            viewModel.loadError.collect { isError ->
                binding.listError.visibility = if (isError == "") View.GONE else View.VISIBLE
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.loading.collect {
                binding.loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if(it){
                    binding.listError.visibility = View.GONE
                    binding.countriesList.visibility = View.GONE
                }
            }
        }
    }

}