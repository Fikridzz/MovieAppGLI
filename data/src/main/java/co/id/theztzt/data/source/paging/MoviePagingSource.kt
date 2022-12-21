package co.id.theztzt.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import co.id.theztzt.data.BuildConfig
import co.id.theztzt.data.source.network.MovieApiClient
import co.id.theztzt.data.source.response.ResultsItem
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(
    private val service: MovieApiClient
) : PagingSource<Int, ResultsItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {
        val position = params.key ?: 1

        return try {
            val response = service.getMovies(BuildConfig.API_KEY, position)
            val data = response.body()?.results

            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = if (position == 1) null else position - 1,
                nextKey = when {
                    position == response.body()?.totalPages -> null
                    response.body()?.results.isNullOrEmpty() -> null
                    else -> position + 1
                }
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}