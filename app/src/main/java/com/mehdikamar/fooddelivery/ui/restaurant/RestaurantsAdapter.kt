package com.mehdikamar.fooddelivery.ui.restaurant

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.mehdikamar.fooddelivery.databinding.RestaurantItemBinding
import com.mehdikamar.fooddelivery.model.Filter
import com.mehdikamar.fooddelivery.model.Restaurant
import com.mehdikamar.fooddelivery.utils.loadRestaurantImage
import com.mehdikamar.fooddelivery.utils.toFiltersText

class RestaurantsAdapter(
    val restaurants: List<Restaurant>,
    val filters: List<Filter>,
    val onItemClicked: (restaurant: Restaurant) -> Unit
) :
    RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>() {

    var filteredRestaurants = restaurants

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RestaurantItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = filteredRestaurants.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        filteredRestaurants.getOrNull(position)?.let { restaurant ->
            holder.viewBinding.restaurantName.text = restaurant.name
            holder.viewBinding.restaurantDeliveryTime.text =
                "${restaurant.deliveryTimeMinutes} mins"
            holder.viewBinding.restaurantRating.text = restaurant.rating
            holder.viewBinding.restaurantImage.loadRestaurantImage(restaurant.imageUrl)
            restaurant.filterIds.toFiltersText(filters)?.let {
                holder.viewBinding.restaurantFilters.text = it
            }
            holder.viewBinding.root.setOnClickListener {
                onItemClicked(restaurant)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterRestaurants(selectedFilters: List<String>) {
        if (selectedFilters.isNotEmpty()) {
            filteredRestaurants = restaurants.filter { it.filterIds.any { it in selectedFilters } }
            notifyDataSetChanged()
        } else {
            filteredRestaurants = restaurants
            notifyDataSetChanged()
        }
    }

    class ViewHolder(val viewBinding: RestaurantItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)
}