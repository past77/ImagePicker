package com.picker.imagepicker.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.picker.imagepicker.data.model.Image
import com.picker.imagepicker.databinding.EachImageBinding
import com.picker.imagepicker.ui.adapter.ImageAdapter.ImageViewHolder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageAdapter @Inject constructor() : ListAdapter<Image, ImageViewHolder>(DifferCallback()) {

    private lateinit var binding: EachImageBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        binding = EachImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder()
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Glide.with(holder.imageView.context).load(currentList[position].imageURL)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class ImageViewHolder : RecyclerView.ViewHolder(binding.root) {
        val imageView: ImageView = binding.eachImageView
    }

    private class DifferCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }

    }
}