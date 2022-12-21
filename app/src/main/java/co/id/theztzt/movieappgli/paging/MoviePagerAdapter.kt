package co.id.theztzt.movieappgli.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.id.theztzt.domain.model.Movie
import co.id.theztzt.movieappgli.databinding.ItemMovieBinding
import co.id.theztzt.movieappgli.utils.ViewExtension.loadImage

class MoviePagerAdapter(private val onItemClick: (id: Int) -> Unit) :
    PagingDataAdapter<Movie, MoviePagerAdapter.ViewHolder>(
        pagerDiver
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Movie?) {
            binding.apply {
                tvTitle.text = data?.title
                imgMovie.loadImage(data?.posterPath ?: "", itemView.context)
                imgMovie.clipToOutline = true
                root.clipToOutline = true
            }
            itemView.setOnClickListener {
                onItemClick(data?.id ?: 0)
            }
        }
    }

    companion object {
        private val pagerDiver = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}