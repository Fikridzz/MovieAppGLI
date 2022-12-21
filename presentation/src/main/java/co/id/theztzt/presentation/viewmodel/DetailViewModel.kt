package co.id.theztzt.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.id.theztzt.common.utils.Resource
import co.id.theztzt.domain.model.Detail
import co.id.theztzt.domain.model.Review
import co.id.theztzt.domain.model.Video
import co.id.theztzt.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    private var _detail = MutableStateFlow<Resource<Detail>>(Resource.Init())
    val detail get() = _detail.asStateFlow()

    private var _review = MutableStateFlow<Resource<MutableList<Review>>>(Resource.Init())
    val review get() = _review.asStateFlow()

    private var _video = MutableStateFlow<Resource<Video>>(Resource.Init())
    val video get() = _video.asStateFlow()

    fun getMovie(id: Int) {
        viewModelScope.launch {
            _detail.value = Resource.Loading()

            movieUseCase.getMovie(id)
                .catch { _detail.value = Resource.Error(it.message) }
                .collectLatest {
                    _detail.value = Resource.Success(it.data)
                }
        }
    }

    fun getMovieReviews(id: Int) {
        viewModelScope.launch {
            _review.value = Resource.Loading()

            movieUseCase.getMovieReviews(id)
                .catch { _review.value = Resource.Error(it.message) }
                .collectLatest {
                    _review.value = Resource.Success(it.data)
                }
        }
    }

    fun getMovieVideo(id: Int) {
        viewModelScope.launch {
            _video.value = Resource.Loading()

            movieUseCase.getMovieVideos(id)
                .catch { _video.value = Resource.Error(it.message) }
                .collectLatest { _video.value = Resource.Success(it.data) }
        }
    }
}