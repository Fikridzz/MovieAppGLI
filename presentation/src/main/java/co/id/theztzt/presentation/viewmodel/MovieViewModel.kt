package co.id.theztzt.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import co.id.theztzt.domain.model.Movie
import co.id.theztzt.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val _movie = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val movie get() = _movie.asStateFlow()

    fun getMovies() {
        viewModelScope.launch {

            movieUseCase.getMovies()
                .cachedIn(this)
                .collectLatest {
                    _movie.value = it
                }
        }
    }
}