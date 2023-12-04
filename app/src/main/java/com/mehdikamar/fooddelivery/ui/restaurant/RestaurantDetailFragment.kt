package com.mehdikamar.fooddelivery.ui.restaurant

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mehdikamar.fooddelivery.R
import com.mehdikamar.fooddelivery.databinding.FragmentRestaurantDetailBinding
import com.mehdikamar.fooddelivery.ui.main.viewmodel.MainViewModel
import com.mehdikamar.fooddelivery.utils.loadRestaurantImage
import com.mehdikamar.fooddelivery.utils.toFiltersText
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class RestaurantDetailFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentRestaurantDetailBinding

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity(), vmFactory)[MainViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestaurantDetailBinding.inflate(inflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    fun initViews() {}

    fun initObservers() {
        mainViewModel.getSelectedRestaurantObserver().observe(viewLifecycleOwner) { restaurant ->
            binding.restaurantName.text = restaurant.name
            binding.restaurantImage.loadRestaurantImage(restaurant.imageUrl)
            val filters = mainViewModel.filtersObserver.value ?: listOf()
            restaurant.filterIds.toFiltersText(filters)?.let {
                binding.restaurantFilters.text = it
            }
            mainViewModel.getOpenStatus(restaurant)
        }

        mainViewModel.getOpenStatusObserver().observe(viewLifecycleOwner) { open ->
            if (open) {
                binding.restaurantOpen.text = getString(R.string.open)
                binding.restaurantOpen.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.positive
                    )
                )
            } else {
                binding.restaurantOpen.text = getString(R.string.closed)
                binding.restaurantOpen.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.negative
                    )
                )
            }
        }

        mainViewModel.getDetailLoadingObserver().observe(viewLifecycleOwner) { loading ->
            if (loading) {
                binding.progress.visibility = View.VISIBLE
                binding.mainLayout.visibility = View.INVISIBLE
            } else {
                binding.progress.visibility = View.INVISIBLE
                binding.mainLayout.visibility = View.VISIBLE
            }
        }
    }
}