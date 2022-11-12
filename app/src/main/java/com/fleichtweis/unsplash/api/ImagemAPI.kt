package com.fleichtweis.unsplash.api

import com.fleichtweis.unsplash.model.UnsplashImagem
import com.fleichtweis.unsplash.model.UnsplashPesquisa
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImagemAPI {

    @GET("photos?client_id=${RetrofitHelper.API_KEY_ACCESS}")
    suspend fun recuperarListaImagens(
        @Query("page") pagina: Int
    ) : Response<UnsplashImagem>

    @GET("search/photos?client_id=${RetrofitHelper.API_KEY_ACCESS}")
    suspend fun recuperarImagensPesquisa(
        @Query("query") pesquisa: String
    ) : Response<UnsplashPesquisa>
}