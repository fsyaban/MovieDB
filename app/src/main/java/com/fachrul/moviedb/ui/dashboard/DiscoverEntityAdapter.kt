package com.fachrul.moviedb.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.fachrul.api_service.service.Const
import com.fachrul.common.entity.database.MovieEntity
import com.fachrul.moviedb.databinding.DashboardItemBinding
import com.fachrul.moviedb.databinding.MovieDashboardItemBinding

class DiscoverEntityAdapter(val navigateToDetail:(Long)->Unit) :
    RecyclerView.Adapter<DiscoverEntityAdapter.DiscoverEntityViewHolder>() {
    inner class DiscoverEntityViewHolder(val binding: DashboardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MovieEntity) {
            binding.data = data
            binding.root.setOnClickListener {
                navigateToDetail((data.movieId))
            }
            data.posterPath.let {
                var requestOptions = RequestOptions()
                requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(42))
                Glide.with(binding.root)
                    .load("https://image.tmdb.org/t/p/w500${it}")
                    .apply(requestOptions)
                    .into(binding.image)
            }
            binding.genre.text = data.genreIds
        }
    }

    val differ = AsyncListDiffer(this, itemCallback)

    companion object {
        val itemCallback = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
                oldItem.movieId == newItem.movieId

            override fun areContentsTheSame(
                oldItem: MovieEntity,
                newItem: MovieEntity
            ): Boolean =
                false
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DiscoverEntityViewHolder =
        DiscoverEntityViewHolder(
            DashboardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DiscoverEntityViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size
}