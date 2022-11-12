package com.fleichtweis.unsplash.model

data class UnsplashPesquisa(
    val results: List<Result>,
    val total: Int,
    val total_pages: Int
)