package com.fachrul.common.entity.movie_review


import com.google.gson.annotations.SerializedName

data class AuthorDetails(
    @SerializedName("avatar_path")
    val avatarPath: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("rating")
    val rating: String?,
    @SerializedName("username")
    val username: String
)