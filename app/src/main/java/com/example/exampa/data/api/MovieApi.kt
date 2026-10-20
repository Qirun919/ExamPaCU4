package com.example.exampa.data.api

import com.example.exampa.data.model.Movie
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("movies")
    suspend fun getMovies(): List<Movie>

    @GET("movies/{id}")
    suspend fun getMovieById(@Path("id") id: Int): Movie

}