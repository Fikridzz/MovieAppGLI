package co.id.theztzt.data.source.network

import co.id.theztzt.data.source.response.DetailResponse
import co.id.theztzt.data.source.response.MovieResponse
import co.id.theztzt.data.source.response.ReviewResponse
import co.id.theztzt.data.source.response.VideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiClient {

    @GET("movie/now_playing")
    suspend fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<MovieResponse>

    @GET("movie/{id}")
    suspend fun getMovie(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
//        @Query("append_to_response") append: String = "video"
    ): Response<DetailResponse>

    @GET("movie/{id}/reviews")
    suspend fun getMovieReviews(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): Response<ReviewResponse>

    @GET("movie/{id}/videos")
    suspend fun getMovieVideos(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
    ): Response<VideoResponse>
}