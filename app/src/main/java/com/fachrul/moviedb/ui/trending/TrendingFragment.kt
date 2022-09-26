package com.fachrul.moviedb.ui.trending

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.paging.LoadState
import com.fachrul.common.common.BaseFragment
import com.fachrul.moviedb.R
import com.fachrul.moviedb.databinding.DiscoverMoviesItemBinding
import com.fachrul.moviedb.databinding.LayoutDiscoverMovieBinding
import com.fachrul.moviedb.ui.discover.DiscoverAdapter
import com.fachrul.moviedb.view_model.TrendingViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrendingFragment : BaseFragment<TrendingViewModel, LayoutDiscoverMovieBinding>() {
    override val vm: TrendingViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.layout_discover_movie
    val adapter = DiscoverAdapter() {
        vm.navigate(TrendingFragmentDirections.trendingToDetail(it))
    }

    override fun initBinding(binding: LayoutDiscoverMovieBinding) {
        super.initBinding(binding)
        binding.recycler.adapter = adapter
        val navBottom = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        navBottom?.visibility = View.GONE
        observeLiveData()
        vm.fetchTrending()
        adapter.addLoadStateListener {
            if (it.refresh is LoadState.Error || it.append is LoadState.Error
                || it.prepend is LoadState.Error
            ) {
                binding.errorContainer.visibility = View.VISIBLE
                binding.recycler.visibility = View.INVISIBLE
                binding.loadingView.visibility = View.INVISIBLE
                binding.retryButton.setOnClickListener {
                    vm.fetchTrending()
                }
            } else if (it.refresh is LoadState.Loading) {
                binding.errorContainer.visibility = View.GONE
                binding.recycler.visibility = View.GONE
                binding.loadingView.visibility = View.VISIBLE
            } else {
                binding.loadingView.visibility = View.INVISIBLE
                binding.errorContainer.visibility = View.GONE
                binding.recycler.visibility = View.VISIBLE
            }
        }
    }

    fun observeLiveData() {
        vm.trendingState.observe(this) {
            lifecycle.coroutineScope.launch {
                adapter.submitData(it)
            }
        }
    }

    override fun onDestroy() {
        val navBottom = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        navBottom?.visibility = View.VISIBLE
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        val navBottom = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        navBottom?.visibility = View.GONE
    }
}