package com.fleichtweis.unsplash.model

data class Result(
    val blur_hash: String,
    val color: String,
    val created_at: String,
    val current_user_collections: List<Any>,
    val description: String,
    val height: Int,
    val id: String,
    val liked_by_user: Boolean,
    val likes: Int,
    val links: LinksXX,
    val urls: UrlsX,
    val user: UserX,
    val width: Int
)