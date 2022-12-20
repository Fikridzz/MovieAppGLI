package co.id.theztzt.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import co.id.theztzt.common.utils.Resource
import co.id.theztzt.data.repository.MovieRepository
import co.id.theztzt.domain.model.Detail
import co.id.theztzt.domain.model.Movie
import co.id.theztzt.domain.utils.DataMapper.mapToDetail
import co.id.theztzt.domain.utils.DataMapper.mapToMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieInteractor(private val movieRepository: MovieRepository) : MovieUseCase {

    override suspend fun getMovies(): Flow<PagingData<Movie>> {
        return movieRepository.getMovies().map { it.map { it.mapToMovie() } }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Detail?>> {
        return movieRepository.getMovie(id).map { Resource.Success(it.data?.mapToDetail()) }
    }
}