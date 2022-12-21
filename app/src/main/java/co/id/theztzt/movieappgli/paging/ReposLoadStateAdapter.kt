package co.id.theztzt.movieappgli.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import co.id.theztzt.movieappgli.databinding.LoadStateFooterBinding

class ReposLoadStateAdapter(
    private val onRefreshClick: () -> Unit
) : LoadStateAdapter<ReposLoadStateAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val view =
            LoadStateFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(
        private val binding: LoadStateFooterBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                Toast.makeText(itemView.context, "Data tidak dapat dimuat", Toast.LENGTH_SHORT)
                    .show()
            }
            binding.btnRefresh.isVisible = loadState is LoadState.Error
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.btnRefresh.setOnClickListener {
                onRefreshClick()
            }
        }
    }

}