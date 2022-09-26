package com.fachrul.common.entity.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
class MovieEntity(

    @field:ColumnInfo(name = "title")
    val title: String,
    @field:ColumnInfo(name = "vote_average")
    val voteAverage: Double,
    @field:ColumnInfo(name = "release_date")
    val releaseDate: String,
    @field:ColumnInfo(name = "genre_ids")
    val genreIds: String,
    @field:ColumnInfo(name = "poster_path")
    val posterPath: String,
    @field:ColumnInfo(name = "backdrop_path")
    val backdropPath: String,
    @field:ColumnInfo(name = "status")
    val status: String,
    @field:ColumnInfo(name = "movie_id")
    val movieId: Long,
    @PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "id")
    var id: Int,
) {
    constructor(
        title: String,
        voteAverage: Double,
        releaseDate: String,
        genreIds: String,
        posterPath: String,
        backdropPath: String,
        status: String,
        movieId: Long
    ) : this(title,voteAverage,releaseDate,genreIds,posterPath,backdropPath,status,movieId,0)
}