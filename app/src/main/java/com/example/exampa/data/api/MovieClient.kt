package com.example.exampa.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieClient {

    private const val BASE_URL = "https://fcapi-1y70.onrender.com/"

    val api: MovieApi by lazy {
        Retrofit.Builder()
            .baseUrl(com.example.exampa.data.api.MovieClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }
}