package com.example.exampa.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exampa.R
import com.example.exampa.databinding.FragmentMovieBinding
import com.example.exampa.ui.adapter.MovieAdapter
import com.example.exampa.ui.base.BaseFragment
import kotlinx.coroutines.launch

class MovieFragment : BaseFragment() {

    private lateinit var binding: FragmentMovieBinding
    private lateinit var adapter: MovieAdapter
    override val viewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents(view: View) {
        adapter = MovieAdapter(emptyList()) { id ->
            findNavController().navigate(
                R.id.action_userFragment_to_detailFragment,
                Bundle().apply { putInt("movie_id", id) }
            )
        }
        binding.rvMovies.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMovies.adapter = adapter


        binding.etSearch.doAfterTextChanged { text ->
            viewModel.search(text.toString())
        }

        binding.btnSortUp.setOnClickListener { viewModel.sortUp() }
        binding.btnSortDown.setOnClickListener { viewModel.sortDown() }
    }

    override fun setupViewModelObserver(view: View) {
        super.setupViewModelObserver(view)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { movies -> adapter.setMovies(movies) }
                }
            }
        }
    }

    override fun onLoadingChanged(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }
}