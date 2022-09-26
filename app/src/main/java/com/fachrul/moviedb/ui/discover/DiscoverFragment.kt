package com.fachrul.moviedb.ui.discover

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.fachrul.common.common.BaseFragment
import com.fachrul.moviedb.R
import com.fachrul.moviedb.databinding.CategoryFragmentBinding
import com.fachrul.moviedb.databinding.LayoutDiscoverMovieBinding
import com.fachrul.moviedb.view_model.DiscoverViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DiscoverFragment:BaseFragment<DiscoverViewModel,LayoutDiscoverMovieBinding>() {
    override val vm: DiscoverViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.layout_discover_movie
    private val navArgs by navArgs<DiscoverFragmentArgs>()
    private val adapter= DiscoverAdapter(){
        vm.navigate(DiscoverFragmentDirections.discoverToDetailFragment(it))
    }
    override fun initBinding(binding: LayoutDiscoverMovieBinding) {
        super.initBinding(binding)
        vm.fetchDiscover(navArgs.genreId)
        binding.recycler.adapter = adapter
        val navBottom = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        navBottom?.visibility = View.GONE
        observeLiveData()

        adapter.addLoadStateListener {
            if (it.refresh is LoadState.Error || it.append is LoadState.Error
                || it.prepend is LoadState.Error) {
                binding.errorContainer.visibility = View.VISIBLE
                binding.recycler.visibility = View.INVISIBLE
                binding.loadingView.visibility = View.INVISIBLE
                binding.retryButton.setOnClickListener {
                    vm.fetchDiscover(navArgs.genreId)
                }
            }else if(it.refresh is LoadState.Loading){
                binding.errorContainer.visibility = View.GONE
                binding.recycler.visibility = View.GONE
                binding.loadingView.visibility = View.VISIBLE
            }
            else {
                binding.loadingView.visibility = View.INVISIBLE
                binding.errorContainer.visibility = View.GONE
                binding.recycler.visibility = View.VISIBLE
            }

        }
    }

    override fun onDestroy() {
        val navBottom = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        navBottom?.visibility =View.VISIBLE
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        val navBottom = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        navBottom?.visibility =View.GONE
    }

    fun observeLiveData(){
        vm.discoverState.observe(this){
            CoroutineScope(Dispatchers.IO).launch {
                adapter.submitData(it)
            }
        }
    }
}