package com.fachrul.moviedb.ui.dashboard

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.fachrul.common.common.BaseFragment
import com.fachrul.common.entity.Result
import com.fachrul.moviedb.R
import com.fachrul.moviedb.databinding.FragmentDashboardBinding
import com.fachrul.moviedb.view_model.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : BaseFragment<DashboardViewModel, FragmentDashboardBinding>() {
    override val vm: DashboardViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.fragment_dashboard

    private val adapterDiscover = DiscoverEntityAdapter() {
        vm.navigate(DashboardFragmentDirections.toDetailFragment(it))
    }
    private val trendingAdapter = DiscoverEntityAdapter {
        vm.navigate(DashboardFragmentDirections.toDetailFragment(it))
    }
    private val topRatedAdapter = DiscoverEntityAdapter() {
        vm.navigate(DashboardFragmentDirections.toDetailFragment(it))
    }

    override fun initBinding(binding: FragmentDashboardBinding) {
        super.initBinding(binding)
        binding.rvDiscover.adapter = adapterDiscover
        binding.rvTopRated.adapter = topRatedAdapter
        binding.rvTrending.adapter = trendingAdapter

        binding.tvSeeAllDiscover.setOnClickListener {
            vm.navigate(DashboardFragmentDirections.homeToDiscover(""))
        }
        binding.tvSeeAllTrending.setOnClickListener {
            vm.navigate(DashboardFragmentDirections.homeToTrending())
        }
        binding.searchView.setOnClickListener {
            vm.navigate(DashboardFragmentDirections.dashboardToSearch())
        }

        binding.retryButton.setOnClickListener {
            vm.getMovies()
        }
        vm.getMovies()
        observeLiveData()
    }


    fun observeLiveData() {
        vm.trendingMovieState?.observe(this) {
            lifecycle.coroutineScope.launch {
                when (it) {
                    is Result.Success -> {
                        trendingAdapter.differ.submitList(it.data)
                    }
                }
            }
        }

        vm.topRatedMovieState?.observe(this) {
            when (it) {
                is Result.Success -> {
                    topRatedAdapter.differ.submitList(it.data)
                }
            }
        }
        vm.discoverState?.observe(this) {
            when (it) {
                is Result.Success -> {
                    binding.errorContainer.visibility = View.GONE
                    binding.loadingView.visibility = View.GONE
                    binding.nestedScrollView.visibility = View.VISIBLE
                    adapterDiscover.differ.submitList(it.data)
                    val imageList = arrayListOf<SlideModel>()
                    it.data.let {
                        for (counter in 0..6) {
                            imageList.add(
                                SlideModel(
                                    "https://image.tmdb.org/t/p/w500${it[counter].backdropPath}",
                                    it[counter].title,
                                    ScaleTypes.FIT
                                )
                            )
                        }
                    }
                    imageList.shuffle()
                    binding.imageSlider.setImageList(imageList)
                }
                is Result.Error -> {
                    binding.errorContainer.visibility = View.VISIBLE
                    binding.loadingView.visibility = View.GONE
                    binding.nestedScrollView.visibility = View.GONE
                }
                is Result.Loading -> {
                    binding.errorContainer.visibility = View.GONE
                    binding.loadingView.visibility = View.VISIBLE
                    binding.nestedScrollView.visibility = View.GONE
                }
            }
        }
    }

//
}