package com.example.exampa.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.exampa.data.api.MovieApi
import com.example.exampa.data.api.MovieClient
import com.example.exampa.data.model.Movie
import com.example.exampa.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(savedStateHandle: SavedStateHandle) : BaseViewModel() {

    private val movieId: Int = checkNotNull(savedStateHandle["movie_id"])

    private val api: MovieApi = MovieClient.api
    private val _state = MutableStateFlow<Movie?>(null)
    val state: StateFlow<Movie?> = _state.asStateFlow()

    init { loadData() }

    fun loadData() {
        viewModelScope.launch {
            safeApiCall {
                api.getMovieById(movieId)
            }?.let { movie ->
                _state.value = movie
            }
        }
    }
}