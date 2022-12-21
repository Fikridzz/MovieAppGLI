package co.id.theztzt.movieappgli.view

import android.webkit.WebChromeClient
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import co.id.theztzt.common.utils.Resource
import co.id.theztzt.domain.model.Detail
import co.id.theztzt.domain.model.Video
import co.id.theztzt.movieappgli.adapter.ReviewAdapter
import co.id.theztzt.movieappgli.databinding.FragmentDetailBinding
import co.id.theztzt.movieappgli.utils.BaseFragment
import co.id.theztzt.movieappgli.utils.ViewExtension.gone
import co.id.theztzt.movieappgli.utils.ViewExtension.loadImage
import co.id.theztzt.movieappgli.utils.ViewExtension.visible
import co.id.theztzt.presentation.viewmodel.DetailViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val detailViewModel: DetailViewModel by viewModel()

    private val detailArgs: DetailFragmentArgs by navArgs()
    private val movieId get() = detailArgs.movieId

    private val reviewAdapter: ReviewAdapter by lazy {
        ReviewAdapter()
    }

    override fun getViewBinding(): FragmentDetailBinding =
        FragmentDetailBinding.inflate(layoutInflater)

    override fun initUI() {
        binding.apply {
            with(rvReviews) {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = reviewAdapter
            }
            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(rvReviews)
        }
    }

    override fun initProcess() {
        detailViewModel.getMovie(movieId)
        detailViewModel.getMovieReviews(movieId)
        detailViewModel.getMovieVideo(movieId)
    }

    override fun initAction() {
    }

    override fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            detailViewModel.detail.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        binding.progressBarDetail.visible()
                    }
                    is Resource.Success -> {
                        binding.progressBarDetail.gone()
                        if (it.data != null) {
                            populateDetail(it.data)
                        }
                    }
                    is Resource.Error -> {
                        binding.progressBarDetail.gone()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            detailViewModel.review.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        binding.progressBarReview.visible()
                    }
                    is Resource.Success -> {
                        binding.progressBarReview.gone()
                        reviewAdapter.submitList(it.data)
                    }
                    is Resource.Error -> {
                        binding.progressBarReview.gone()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            detailViewModel.video.collectLatest {
                when (it) {
                    is Resource.Success -> {
                        if (it.data != null) populateVideo(it.data)
                    }
                    is Resource.Error -> {}
                    else -> Unit
                }
            }
        }
    }

    private fun populateVideo(data: Video?) {
        binding.apply {
            val url = "https://www.youtube.com/embed/${data?.key}"
            webView.webChromeClient = WebChromeClient()
            webView.settings.javaScriptEnabled = true
            webView.loadUrl(url)
        }
    }

    private fun populateDetail(data: Detail?) {
        binding.apply {
            tvTitle.text = data?.title
            tvGenres.text = data?.genres
            tvDate.text = data?.releaseDate
            tvTime.text = data?.runtime
            tvOverview.text = data?.overview
            imgMovie.loadImage(data?.backdropPath ?: "", requireContext())
        }
    }

}