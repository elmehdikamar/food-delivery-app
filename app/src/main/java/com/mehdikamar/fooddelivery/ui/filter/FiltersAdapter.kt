package com.mehdikamar.fooddelivery.ui.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mehdikamar.fooddelivery.databinding.FilterItemBinding
import com.mehdikamar.fooddelivery.model.Filter

class FiltersAdapter(
    val filters: List<Filter>,
    val onFilterSelected: (filterId: String) -> Unit,
    val onFilterUnselected: (filterId: String) -> Unit
) :
    RecyclerView.Adapter<FiltersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return FiltersAdapter.ViewHolder(
            FilterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = filters.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        filters.getOrNull(position)?.let { filter ->
            holder.viewBinding.filterTitle.text = filter.name
            Glide.with(holder.viewBinding.filterIcon.context)
                .load(filter.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.viewBinding.filterIcon)
            holder.viewBinding.root.setOnClickListener {
                if (it.isSelected) onFilterUnselected(filter.id)
                else onFilterSelected(filter.id)
                it.isSelected = !it.isSelected
            }
        }
    }

    class ViewHolder(val viewBinding: FilterItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)
}