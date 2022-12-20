package co.id.theztzt.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.id.theztzt.common.utils.Resource
import co.id.theztzt.domain.model.Detail
import co.id.theztzt.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val _detail = MutableStateFlow<Resource<Detail>>(Resource.Init())
    val detail get() = _detail.asStateFlow()

    fun getMovies(id: Int) {
        viewModelScope.launch {
            _detail.value = Resource.Loading()

            movieUseCase.getMovie(id)
                .catch { _detail.value = Resource.Error(it.message) }
                .collectLatest {
                    _detail.value = Resource.Success(it.data)
                }
        }
    }
}