package co.id.theztzt.domain.usecase

import androidx.paging.PagingData
import co.id.theztzt.common.utils.Resource
import co.id.theztzt.domain.model.Detail
import co.id.theztzt.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {

    suspend fun getMovies(): Flow<PagingData<Movie>>

    suspend fun getMovie(id: Int): Flow<Resource<Detail?>>
}