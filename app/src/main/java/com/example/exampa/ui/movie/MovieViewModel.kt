package com.example.exampa.ui.movie

import androidx.lifecycle.viewModelScope
import com.example.exampa.data.api.MovieApi
import com.example.exampa.data.api.MovieClient
import com.example.exampa.data.model.Movie
import com.example.exampa.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieViewModel : BaseViewModel() {

    private val api: MovieApi = MovieClient.api
    private val _state = MutableStateFlow<List<Movie>>(emptyList())
    val state: StateFlow<List<Movie>> = _state.asStateFlow()


    private var allMovies: List<Movie> = emptyList()

    private var keyword = ""

    private var sortAscending: Boolean? = null

    init {
        loadData()
    }


    fun loadData() {
        viewModelScope.launch {
            safeApiCall {
                api.getMovies()
            }?.let { movies ->
                allMovies = movies
                _state.value = movies
            }
        }
    }

    fun search(text: String) {
        keyword = text
        applyFilters()
    }

    fun sortUp() {
        sortAscending = true
        applyFilters()
    }

    /** 价格高 → 低 */
    fun sortDown() {
        sortAscending = false
        applyFilters()
    }

    private fun applyFilters() {
        var result = allMovies

        if (keyword.isNotBlank()) {
            result = result.filter { it.title.contains(keyword, ignoreCase = true) }
        }

        result = when (sortAscending) {
            true -> result.sortedBy { it.rating }
            false -> result.sortedByDescending { it.rating }
            null -> result
        }

        _state.value = result
    }
}

