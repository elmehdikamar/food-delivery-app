package com.mehdikamar.fooddelivery.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mehdikamar.fooddelivery.model.Filter

fun List<String>.toFiltersText(allFilters: List<Filter>): String? {
    if (allFilters.isNotEmpty() && this.isNotEmpty()) {
        var filtersText = ""
        this.forEachIndexed { index, filterId ->
            val filter = allFilters.firstOrNull { it.id == filterId }
            filter?.let {
                filtersText += it.name
                if (index != this.size - 1) filtersText += " · "
            }
        }
        return filtersText
    }
    return null
}

fun ImageView.loadRestaurantImage(imageUrl: String?) {
    Glide.with(this.context)
        .load(imageUrl)
        .apply(
            RequestOptions.centerCropTransform()
                .transform(CenterCrop(), GranularRoundedCorners(12f, 12f, 0f, 0f))
        )
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}