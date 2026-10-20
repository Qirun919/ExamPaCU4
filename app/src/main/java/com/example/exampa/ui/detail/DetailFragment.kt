package com.example.exampa.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.exampa.databinding.FragmentDetailBinding
import com.example.exampa.ui.base.BaseFragment
import kotlinx.coroutines.launch

class DetailFragment : BaseFragment() {

    private lateinit var binding: FragmentDetailBinding
    override val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents(view: View) {
        binding.btnBack.setOnClickListener { findNavController().navigateUp() }
    }

    override fun setupViewModelObserver(view: View) {
        super.setupViewModelObserver(view)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { user ->
                    user?.let {
                        binding.tvTitle.text = it.title
                        binding.tvRating.text = "Rating: ${it.rating}"
                        binding.tvDirector.text = "Director: ${it.director}"
                        binding.tvReleaseDate.text = "Release Date: ${it.release_date}"
                        binding.tvLength.text = "Length: ${it.length} Minutes"
                        binding.tvOverview.text = "Overview: ${it.overview}"

                    }
                }
            }
        }
    }

    override fun onLoadingChanged(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }
}