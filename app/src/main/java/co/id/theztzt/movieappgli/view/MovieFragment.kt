package co.id.theztzt.movieappgli.view

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import co.id.theztzt.movieappgli.databinding.FragmentMovieBinding
import co.id.theztzt.movieappgli.paging.MoviePagerAdapter
import co.id.theztzt.movieappgli.paging.ReposLoadStateAdapter
import co.id.theztzt.movieappgli.utils.BaseFragment
import co.id.theztzt.movieappgli.utils.ViewExtension.gone
import co.id.theztzt.movieappgli.utils.ViewExtension.visible
import co.id.theztzt.presentation.viewmodel.MovieViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : BaseFragment<FragmentMovieBinding>() {

    private val movieViewModel: MovieViewModel by viewModel()
    private val moviePager: MoviePagerAdapter by lazy {
        MoviePagerAdapter(
            onItemClick = {
                val action = MovieFragmentDirections.actionMovieFragmentToDetailFragment(it)
                findNavController().navigate(action)
            }
        )
    }

    override fun getViewBinding(): FragmentMovieBinding =
        FragmentMovieBinding.inflate(layoutInflater)

    override fun initUI() {
        binding.apply {
            with(rvMovie) {
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = moviePager.withLoadStateFooter(
                    footer = ReposLoadStateAdapter(onRefreshClick = { moviePager.retry() })
                )
            }

            moviePager.addLoadStateListener { loadState ->
                when (loadState.source.refresh) {
                    is LoadState.NotLoading -> {
                        rvMovie.visible()
                        progressBar.gone()
                        if (moviePager.itemCount == 0) binding.groupError.visible()
                    }
                    is LoadState.Loading -> {
                        rvMovie.gone()
                        progressBar.visible()
                        groupError.gone()
                    }
                    is LoadState.Error -> {
                        rvMovie.gone()
                        progressBar.visible()
                        groupError.gone()
                    }
                }
            }
        }
    }

    override fun initProcess() {
        movieViewModel.getMovies()
    }

    override fun initAction() {
        binding.btnRetry.setOnClickListener { movieViewModel.getMovies() }
    }

    override fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            movieViewModel.movie.collectLatest {
                moviePager.submitData(it)
            }
        }
    }
}