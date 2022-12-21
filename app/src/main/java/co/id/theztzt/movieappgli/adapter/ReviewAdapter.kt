package co.id.theztzt.movieappgli.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.id.theztzt.domain.model.Review
import co.id.theztzt.movieappgli.databinding.ItemReviewBinding
import co.id.theztzt.movieappgli.utils.ViewExtension.loadImage

class ReviewAdapter : ListAdapter<Review, ReviewAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Review) {
            binding.apply {
                tvUsername.text = data.username
                tvOverview.text = data.content
                imgUser.clipToOutline = true
                if (data.avatarPath?.isNotEmpty() == true) imgUser.loadImage(
                    data.avatarPath ?: "",
                    itemView.context
                )
            }
        }
    }

    companion object {
        private val diffCallback = object : ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem == newItem
            }
        }
    }
}