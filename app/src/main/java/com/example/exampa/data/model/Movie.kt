package com.example.exampa.data.model

import android.text.format.DateFormat
import com.google.gson.internal.bind.DefaultDateTypeAdapter
import java.util.Date

data class Movie(
    val id: Int,
    val title: String,
    val director: String,
    val overview: String,
    val length: Int,
    val rating: Double,
    val release_date: String
)