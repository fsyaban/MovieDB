package com.fachrul.moviedb.ui.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.fachrul.api_service.service.Const.getGenreString
import com.fachrul.common.entity.discover.Result
import com.fachrul.moviedb.databinding.DiscoverMoviesItemBinding
import com.fachrul.moviedb.databinding.LayoutDiscoverMovieBinding

class DiscoverAdapter(val navigateToDetail:(Long)->Unit) :
    PagingDataAdapter<Result, DiscoverAdapter.DiscoverViewHolder>(itemCallback) {
    inner class DiscoverViewHolder(val binding: DiscoverMoviesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data:Result?) {
            binding.genre.text = data?.genreIds?.joinToString(separator = ", ") { getGenreString(it) }
            binding.data = data
            binding.root.setOnClickListener {
                navigateToDetail((data?.id)?:0)
            }
            data?.posterPath?.let {
                var requestOptions = RequestOptions()
                requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(42))
                Glide.with(binding.root)
                    .load("https://image.tmdb.org/t/p/w500${it}")
                    .apply(requestOptions)
                    .into(binding.image)
            }
        }
    }

    companion object {
        val itemCallback = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean = false

        }
    }

    override fun onBindViewHolder(holder: DiscoverViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverViewHolder =
        DiscoverViewHolder(
            DiscoverMoviesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

}