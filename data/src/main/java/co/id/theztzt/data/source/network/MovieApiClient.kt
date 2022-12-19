package co.id.theztzt.data.source.network

import co.id.theztzt.data.source.response.DetailResponse
import co.id.theztzt.data.source.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiClient {

    @GET("movie/now_playing")
    suspend fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ) : Response<MovieResponse>

    @GET("movie/{id}")
    suspend fun getMovie(
        @Query("api_key") apiKey: String,
        @Path("id") id: Int
    ) : Response<DetailResponse>
}