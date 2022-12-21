package co.id.theztzt.data.repository

import androidx.paging.PagingData
import co.id.theztzt.common.utils.Resource
import co.id.theztzt.data.source.response.DetailResponse
import co.id.theztzt.data.source.response.ResultsItem
import co.id.theztzt.data.source.response.ResultsReview
import co.id.theztzt.data.source.response.VideoResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovies(): Flow<PagingData<ResultsItem>>

    suspend fun getMovie(id: Int): Flow<Resource<DetailResponse>>

    suspend fun getMovieReviews(id: Int): Flow<Resource<List<ResultsReview?>>>

    suspend fun getMovieVideos(id: Int): Flow<Resource<VideoResponse>>
}