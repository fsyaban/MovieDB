package com.fachrul.moviedb.ui.search

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.paging.LoadState
import com.fachrul.common.common.BaseFragment
import com.fachrul.moviedb.R
import com.fachrul.moviedb.databinding.SearchFragmentBinding
import com.fachrul.moviedb.ui.discover.DiscoverAdapter
import com.fachrul.moviedb.view_model.SearchViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<SearchViewModel, SearchFragmentBinding>() {
    override val vm: SearchViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.search_fragment
    val adapter = DiscoverAdapter() {
        vm.navigate(SearchFragmentDirections.searchToDetail(it))
    }

    override fun initBinding(binding: SearchFragmentBinding) {
        super.initBinding(binding)
        binding.recycler.adapter = adapter
        val navBottom = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        navBottom?.visibility = View.GONE
        adapter.addLoadStateListener {
            if (it.refresh is LoadState.Error || it.append is LoadState.Error
                || it.prepend is LoadState.Error
            ) {
                binding.errorContainer.visibility = View.VISIBLE
                binding.recycler.visibility = View.INVISIBLE
                binding.loadingView.visibility = View.INVISIBLE
                binding.searchView.visibility = View.INVISIBLE
                binding.retryButton.setOnClickListener {
                    vm.fetchMovie("a")
                }
            } else if (it.refresh is LoadState.Loading) {
                binding.errorContainer.visibility = View.GONE
                binding.recycler.visibility = View.GONE
                binding.searchView.visibility = View.VISIBLE
                binding.loadingView.visibility = View.VISIBLE
            } else {
                binding.loadingView.visibility = View.INVISIBLE
                binding.errorContainer.visibility = View.GONE
                binding.recycler.visibility = View.VISIBLE
                binding.searchView.visibility = View.VISIBLE
            }
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                vm.fetchMovie(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.isNotEmpty()) vm.fetchMovie(newText)
                }

                return true
            }
        })
        observeLiveData()


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

    fun observeLiveData() {
        vm.searchMovieState.observe(this) {
            lifecycle.coroutineScope.launch {
                adapter.submitData(it)
            }
        }
    }
}