package co.id.theztzt.domain.utils

import co.id.theztzt.data.source.response.DetailResponse
import co.id.theztzt.data.source.response.ResultsItem
import co.id.theztzt.data.source.response.ResultsReview
import co.id.theztzt.data.source.response.ResultsVideo
import co.id.theztzt.domain.BuildConfig
import co.id.theztzt.domain.model.Detail
import co.id.theztzt.domain.model.Movie
import co.id.theztzt.domain.model.Review
import co.id.theztzt.domain.model.Video
import java.text.SimpleDateFormat
import java.util.*

object DataMapper {

    fun ResultsItem.mapToMovie(): Movie =
        Movie(
            id = id ?: 0,
            title = title ?: "",
            overview = overview ?: "",
            genres = genreIds?.map { it.getGenre() }?.joinToString(separator = ", ") { it } ?: "",
            popularity = popularity ?: 0.0,
            voteAverage = voteAverage ?: 0.0,
            posterPath = posterPath?.loadImage() ?: "",
            backdropPath = backdropPath?.loadImageOriginal() ?: "",
            releaseDate = releaseDate?.convertDate() ?: ""
        )

    fun DetailResponse.mapToDetail(): Detail =
        Detail(
            id = id ?: 0,
            title = title ?: "",
            overview = overview ?: "",
            genres = genres?.joinToString(separator = " • ") { it.name } ?: "",
            runtime = runtime?.convertTime() ?: "",
            popularity = popularity ?: 0.0,
            voteAverage = voteAverage ?: 0.0,
            releaseDate = releaseDate?.convertDate() ?: "",
            posterPath = posterPath?.loadImage() ?: "",
            backdropPath = backdropPath?.loadImageOriginal() ?: "",
        )

    fun ResultsVideo.mapToVideo(): Video =
        Video(
            id = this.id,
            name = this.name,
            key = this.key,
            site = this.site,
            type = this.type,
            publishedAt = this.publishedAt
        )

    fun List<ResultsReview?>?.toListReview(): List<Review> {
        val newData = mutableListOf<Review>()
        this?.forEach {
            newData.add(it?.mapToReview()!!)
        }
        return newData
    }

    fun ResultsReview.mapToReview(): Review =
        Review(
            id = this.id,
            username = this.authorDetails?.username,
            avatarPath = this.authorDetails?.avatarPath?.loadImage(),
            content = this.content,
            rating = this.authorDetails?.rating,
            createdAt = this.createdAt ?: "-"
        )

    private fun Int.getGenre() =
        when (this) {
            28 -> "Action"
            12 -> "Adventure"
            16 -> "Animation"
            35 -> "Comedy"
            80 -> "Crime"
            99 -> "Documentary"
            18 -> "Drama"
            10751 -> "Family"
            14 -> "Fantasy"
            36 -> "History"
            27 -> "Horror"
            10402 -> "Music"
            9648 -> "Mystery"
            10749 -> "Romance"
            878 -> "Science Fiction"
            10770 -> "TV Movie"
            53 -> "Thriller"
            10752 -> "War"
            37 -> "Western"
            else -> "No genres available for $this"
        }

    private fun String.convertDate(): String {
        return if (this == "") {
            "Data kosong"
        } else {
            val input = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this)
            val date = Date(input.time)
            val convert = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            convert.format(date)
        }
    }

    private fun Int.convertTime(): String {
        val hours = this / 60
        val minute = this % 60

        return if (hours == 0) {
            "$minute minute"
        } else {
            "$hours hours $minute minute"
        }
    }

    private fun String.loadImage(): String {
        val url = BuildConfig.BASE_URL_IMAGE
        val size = "w500"
        return url + size + this
    }

    private fun String.loadImageOriginal(): String {
        val url = BuildConfig.BASE_URL_IMAGE
        val size = "original"
        return url + size + this
    }
}