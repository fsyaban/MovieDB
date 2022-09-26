package com.fachrul.moviedb.ui.category

import android.view.View
import androidx.fragment.app.viewModels
import com.fachrul.common.common.BaseFragment
import com.fachrul.common.entity.Result
import com.fachrul.moviedb.R
import com.fachrul.moviedb.databinding.CategoryFragmentBinding
import com.fachrul.moviedb.view_model.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment<CategoryViewModel, CategoryFragmentBinding>() {
    override val vm: CategoryViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.category_fragment
    private val adapter = CategoryAdapter(isGenreSelected = {
        vm.selectedGenre.contains(it)
    }, toggle = {
        if (vm.selectedGenre.contains(it)) vm.selectedGenre.remove(it) else vm.selectedGenre.add(it)
    })

    override fun initBinding(binding: CategoryFragmentBinding) {
        super.initBinding(binding)
        binding.rvGenre.adapter = adapter
        observeLiveData()
        binding.retryButton.setOnClickListener {
            vm.fetchGenre()
        }
        binding.nextButton.setOnClickListener {
            vm.navigate(CategoryFragmentDirections.toDiscoverFragment(vm.selectedGenre.joinToString(separator = ",") { it.id.toString() }))
        }
    }

    fun observeLiveData() {
        observeResponseData(vm.genreState, success = {
            binding.errorContainer.visibility = View.GONE
            binding.loadingView.visibility = View.GONE
            binding.nextButton.visibility=View.VISIBLE
            binding.rvGenre.visibility =View.VISIBLE
            adapter.differ.submitList(it)
        }, error = {
            binding.errorContainer.visibility = View.VISIBLE
            binding.loadingView.visibility = View.GONE
            binding.nextButton.visibility=View.GONE
            binding.rvGenre.visibility =View.GONE
        }, loading = {
            binding.errorContainer.visibility = View.GONE
            binding.loadingView.visibility = View.VISIBLE
            binding.nextButton.visibility=View.GONE
            binding.rvGenre.visibility =View.GONE
        })
    }
}