package com.example.exampa.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exampa.data.model.Movie
import com.example.exampa.databinding.ItemMovieBinding


class MovieAdapter(
    private var movies: List<Movie>,
    private val onClick: (Int) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val movie = movies[position]
        if (holder is MovieViewHolder) {
            holder.bind(movie)
        }
    }

    override fun getItemCount() = movies.size


    fun setMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(
        val binding: ItemMovieBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.run {
                tvTitle.text = movie.title
                tvRating.text = "Rating: %.2f".format(movie.rating)
                tvDirector.text = movie.director
                tvReleaseDate.text = "Release Date: ${movie.release_date}"

                cvMovie.setOnClickListener {
                    onClick(movie.id)
                }
            }

        }
    }
}