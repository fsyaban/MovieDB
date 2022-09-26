package com.fachrul.moviedb.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fachrul.common.entity.movie_review.Result
import com.fachrul.moviedb.databinding.MovieReviewItemBinding

class MovieReviewAdapter : PagingDataAdapter<Result, MovieReviewAdapter.MovieReviewHolder>(
    diffCallback) {
    inner class MovieReviewHolder(val binding: MovieReviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Result?) {
            binding.data = data
            data?.let {
                it.authorDetails.avatarPath?.let { avatarPath ->
                    if (avatarPath.substring(0, 5).equals("/http")) {
                        Glide.with(binding.root).load(avatarPath.substring(1))
                            .into(binding.image)
                    } else {
                        Glide.with(binding.root)
                            .load("https://www.themoviedb.org/t/p/w300_and_h300_face${avatarPath}")
                            .into(binding.image)
                    }
                }

            }
        }
    }
    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<Result>(){
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean =
                false
        }
    }

    override fun onBindViewHolder(holder: MovieReviewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieReviewHolder =
        MovieReviewHolder(
            MovieReviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
}