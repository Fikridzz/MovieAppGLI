package co.id.theztzt.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import co.id.theztzt.common.utils.Resource
import co.id.theztzt.data.BuildConfig
import co.id.theztzt.data.source.network.MovieApiClient
import co.id.theztzt.data.source.paging.MoviePagingSource
import co.id.theztzt.data.source.response.DetailResponse
import co.id.theztzt.data.source.response.ResultsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject

class MovieDataStore(
    private val movieApi: MovieApiClient
) : MovieRepository {

    private val apiKey = BuildConfig.API_KEY

    override suspend fun getMovies(): Flow<PagingData<ResultsItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5
            ),
            pagingSourceFactory = { MoviePagingSource(movieApi) }
        ).flow
    }

    override suspend fun getMovie(id: Int): Flow<Resource<DetailResponse>> {
        return flow {
            val response = movieApi.getMovie(apiKey, id)
            val data = response.body()
            if (data != null) {
                emit(Resource.Success(data))
            } else {
                val errorBody =
                    JSONObject(response.errorBody()?.string() ?: "Internal server error")
                val errorMsg = errorBody.getString("status_message")
                emit(Resource.Error(errorMsg))
            }
        }
    }

}