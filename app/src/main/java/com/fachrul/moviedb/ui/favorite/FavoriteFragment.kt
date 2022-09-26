package com.fachrul.moviedb.ui.favorite

import androidx.fragment.app.viewModels
import com.fachrul.common.common.BaseFragment
import com.fachrul.moviedb.R
import com.fachrul.moviedb.databinding.DiscoverMoviesItemBinding
import com.fachrul.moviedb.databinding.LayoutDiscoverMovieBinding
import com.fachrul.moviedb.view_model.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment:BaseFragment<FavoriteViewModel,LayoutDiscoverMovieBinding>() {
    override val vm: FavoriteViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.layout_discover_movie
    val adapter = FavoriteAdapter(){
        vm.navigate(FavoriteFragmentDirections.favoriteToDetailFragment(it))
    }

    override fun initBinding(binding: LayoutDiscoverMovieBinding) {
        super.initBinding(binding)
        binding.recycler.adapter = adapter
        observeLiveData()
    }
    fun observeLiveData(){
        vm.favoriteState?.observe(this){
            adapter.differ.submitList(it)
        }
    }
}