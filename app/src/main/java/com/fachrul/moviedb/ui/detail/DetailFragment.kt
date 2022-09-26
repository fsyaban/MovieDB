package com.fachrul.moviedb.ui.detail

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.fachrul.common.common.BaseFragment
import com.fachrul.moviedb.R
import com.fachrul.moviedb.databinding.ActivityMainBinding
import com.fachrul.moviedb.databinding.MovieDetailLayoutBinding
import com.fachrul.moviedb.ui.MainActivity
import com.fachrul.moviedb.view_model.DetailViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailViewModel, MovieDetailLayoutBinding>() {
    override val vm: DetailViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.movie_detail_layout
    private val navArgs by navArgs<DetailFragmentArgs>()
    private val videoAdapter = MovieVideoAdapter {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=$it")
            )
        )
    }
    private val reviewAdapter = MovieReviewAdapter()

    override fun initBinding(binding: MovieDetailLayoutBinding) {
        super.initBinding(binding)
        val navBottom = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        navBottom?.visibility = View.GONE
        binding.rvVideo.adapter = videoAdapter
        binding.rvReview.adapter = reviewAdapter

        vm.fetchMovieDetail(navArgs.movieId)
        observeLiveData()
    }

    override fun onDestroy() {
        val navBottom = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        navBottom?.visibility = View.VISIBLE
        super.onDestroy()
    }

    fun observeLiveData() {
        vm.isFavoriteLiveData?.observe(this) {
            binding.isFavorite = it
        }
        observeResponseData(vm.detailState, success = {

            binding.movieDetailView.visibility = View.VISIBLE
            binding.proggress.visibility = View.GONE
            binding.errorContainer.visibility = View.GONE
            binding.btnFavorite.visibility = View.VISIBLE

            binding.btnFavorite.setOnClickListener { view ->
                binding.isFavorite = !(binding.isFavorite ?: false)
                vm.toggle(it.first)
            }
            binding.data = it.first
            binding.genre.text = it.first.genres.joinToString(separator = ", ") { it.name }
            Glide.with(binding.root)
                .load("https://image.tmdb.org/t/p/w500${it.first.backdropPath}")
                .into(binding.imgBackground)
            Glide.with(binding.root)
                .load("https://image.tmdb.org/t/p/w500${it.first.posterPath}")
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(24)))
                .into(binding.imgPoster)
            if (it.second.results.isNotEmpty()) {
                showVideo(it.second.results[0].key)
            }
            videoAdapter.differ.submitList(it.second.results)
        }, error = {
            binding.movieDetailView.visibility = View.GONE
            binding.proggress.visibility = View.GONE
            binding.errorContainer.visibility = View.VISIBLE
            binding.btnFavorite.visibility = View.INVISIBLE
            binding.retryButton.setOnClickListener {
                vm.fetchMovieDetail(navArgs.movieId)
            }
            Log.e("INET",it)
        }, loading = {
            binding.movieDetailView.visibility = View.GONE
            binding.proggress.visibility = View.VISIBLE
            binding.errorContainer.visibility = View.GONE
            binding.btnFavorite.visibility = View.INVISIBLE
        })

        vm.reviewState.observe(this) {
            CoroutineScope(Dispatchers.IO).launch {
                reviewAdapter.submitData(it)
            }
        }
    }


    private fun showVideo(videoId: String) {

        val listener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)

                youTubePlayer.cueVideo(videoId, 0f)

                val defaultPlayerUiController =
                    DefaultPlayerUiController(binding.fragmentVideoTrailer, youTubePlayer)
                binding.fragmentVideoTrailer.setCustomPlayerUi(defaultPlayerUiController.rootView)
            }
        }

        val option = IFramePlayerOptions.Builder().controls(0).build()
        binding.fragmentVideoTrailer.initialize(listener, option)

    }
}