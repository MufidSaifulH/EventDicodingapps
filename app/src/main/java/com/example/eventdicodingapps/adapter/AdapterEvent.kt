package com.example.eventdicodingapps.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.eventdicodingapps.DetailActivity
import com.example.eventdicodingapps.R
import com.example.eventdicodingapps.data.local.entity.EventEntity
import com.example.eventdicodingapps.databinding.Item2HorizontalBinding
import com.example.eventdicodingapps.databinding.ItemEventBinding
import com.example.eventdicodingapps.databinding.ItemNormalveriBinding

class AdapterEvent(private var isLoading: Boolean = true, private val onFavoriteClick: (EventEntity) -> Unit
) :
    ListAdapter<EventEntity, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int {
        return if (isLoading) VIEW_TYPE_SHIMMER else VIEW_TYPE_DATA
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SHIMMER) {
            val binding = Item2HorizontalBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            ShimmerViewHolder(binding)
        } else {
            val binding = ItemNormalveriBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            MyViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder && !isLoading) {
            val event = getItem(position)
            holder.bind(event)
            val favoriteImageView = holder.binding.favoriteImageView
            favoriteImageView.setImageResource(
                if (event.isFavorite == true) R.drawable.ic_favorite_fill
                else R.drawable.ic_favorite_border
            )
            favoriteImageView.setOnClickListener {
                onFavoriteClick(event)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (isLoading) 5 else super.getItemCount()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setLoadingState(isLoading: Boolean) {
        this.isLoading = isLoading
        notifyDataSetChanged()
    }

    class ShimmerViewHolder(binding: Item2HorizontalBinding) :
        RecyclerView.ViewHolder(binding.root)

    class MyViewHolder(val binding: ItemNormalveriBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventEntity) {
            binding.titleTextView.text = event.name
            binding.ownerTextView.text = event.ownerName
            binding.chip.text = "${event.category}"
            Glide.with(binding.root.context)
                .load(event.imageLogo)
                .transform(RoundedCorners(16))
                .into(binding.imageView)
            itemView.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra("EXTRA_EVENT", event)
                binding.root.context.startActivity(intent)
            }
        }
    }

    companion object {
        private const val VIEW_TYPE_SHIMMER = 0
        private const val VIEW_TYPE_DATA = 1

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventEntity>() {
            override fun areItemsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

}
//class VerticalAdapter(
//    private var isLoading: Boolean = true,
//    private val onFavoriteClick: (EventEntity) -> Unit
//) : ListAdapter<EventEntity, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
//
//    override fun getItemViewType(position: Int) = if (isLoading) VIEW_TYPE_SHIMMER else VIEW_TYPE_DATA
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
//        if (viewType == VIEW_TYPE_SHIMMER)
//            ShimmerViewHolder(Item2HorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
//        else
//            MyViewHolder(ItemNormalveriBinding.inflate(LayoutInflater.from(parent.context), parent, false))
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if (holder is MyViewHolder && !isLoading) {
//            val event = getItem(position)
//            holder.bind(event)
//            holder.binding.favoriteImageView.apply {
//                setImageResource(if (event.isFavorite == true) R.drawable.ic_favorite_fill else R.drawable.ic_favorite_border)
//                setOnClickListener { onFavoriteClick(event) }
//            }
//        }
//    }
//
//    override fun getItemCount() = if (isLoading) 5 else super.getItemCount()
//
//    @SuppressLint("NotifyDataSetChanged")
//    fun setLoadingState(isLoading: Boolean) {
//        this.isLoading = isLoading
//        notifyDataSetChanged()
//    }
//
//    class ShimmerViewHolder(binding: Item2HorizontalBinding) : RecyclerView.ViewHolder(binding.root)
//
//    class MyViewHolder(val binding: ItemNormalveriBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(event: EventEntity) = binding.apply {
//            titleTextView.text = event.name
//            ownerTextView.text = event.ownerName
//            chip.text = event.category
//            Glide.with(root.context).load(event.imageLogo).transform(RoundedCorners(16)).into(imageView)
//            root.setOnClickListener {
//                root.context.startActivity(Intent(root.context, DetailActivity::class.java).apply {
//                    putExtra("EXTRA_EVENT", event)
//                })
//            }
//        }
//    }
//
//    companion object {
//        private const val VIEW_TYPE_SHIMMER = 0
//        private const val VIEW_TYPE_DATA = 1
//
//        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventEntity>() {
//            override fun areItemsTheSame(oldItem: EventEntity, newItem: EventEntity) = oldItem.id == newItem.id
//            @SuppressLint("DiffUtilEquals")
//            override fun areContentsTheSame(oldItem: EventEntity, newItem: EventEntity) = oldItem == newItem
//        }
//    }
//}
