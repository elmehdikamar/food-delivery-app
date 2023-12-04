package com.mehdikamar.fooddelivery.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mehdikamar.fooddelivery.databinding.ActivityMainBinding
import com.mehdikamar.fooddelivery.ui.filter.FiltersAdapter
import com.mehdikamar.fooddelivery.ui.main.viewmodel.MainViewModel
import com.mehdikamar.fooddelivery.ui.restaurant.RestaurantDetailFragment
import com.mehdikamar.fooddelivery.ui.restaurant.RestaurantsAdapter
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class RestaurantMainActivity : DaggerAppCompatActivity() {

    companion object {
        const val RESTAURANT_DETAIL_TAG: String = "RestaurantDetailFragment"
    }

    lateinit var binding: ActivityMainBinding

    private lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        mainViewModel = ViewModelProvider(this, vmFactory)[MainViewModel::class.java]
        val view = binding.root
        setContentView(view)
        initViews()
        initObservers()
        mainViewModel.getRestaurants()
    }

    private fun initViews() {
        binding.recyclerViewRestaurants.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewFilters.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun initObservers() {
        mainViewModel.getLoadingObserver().observe(this) { loading ->
            if (loading) {
                binding.mainLayout.visibility = View.GONE
                binding.progress.visibility = View.VISIBLE
            } else {
                binding.mainLayout.visibility = View.VISIBLE
                binding.progress.visibility = View.GONE
            }
        }

        mainViewModel.getFiltersObserver().observe(this) { filters ->
            if (filters.isNotEmpty()) {
                binding.recyclerViewFilters.adapter = FiltersAdapter(filters,
                    onFilterSelected = {
                        mainViewModel.addFilter(it)
                    },
                    onFilterUnselected = {
                        mainViewModel.removeFilter(it)
                    })
            }
        }

        mainViewModel.getRestaurantsObserver().observe(this) { restaurants ->
            if (restaurants.isNotEmpty()) {
                binding.recyclerViewRestaurants.adapter =
                    RestaurantsAdapter(
                        restaurants,
                        mainViewModel.filtersObserver.value ?: listOf()
                    ) { restaurant ->
                        RestaurantDetailFragment().show(
                            supportFragmentManager,
                            Companion.RESTAURANT_DETAIL_TAG
                        )
                        mainViewModel.selectRestaurant(restaurant)
                    }
            }
        }

        mainViewModel.getSelectedFiltersObserver().observe(this) { filterIds ->
            (binding.recyclerViewRestaurants.adapter as RestaurantsAdapter?)?.filterRestaurants(
                filterIds
            )
        }

        mainViewModel.getErrorObserver().observe(this) {
            it?.let {
                Toast.makeText(applicationContext, it.reason, Toast.LENGTH_LONG).show()
            }
        }
    }
}