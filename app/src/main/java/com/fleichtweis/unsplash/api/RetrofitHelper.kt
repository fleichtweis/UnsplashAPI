package com.fleichtweis.unsplash.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {

    companion object{
        const val API_KEY_ACCESS = "OgaSaK0kziXmoZpNqt2Ft518e-rEJT6y0QYW1RHJV_M"
        const val API_KEY_SECRET = "MpmtQEvXMzv_5g2mkVtNYvto6jqJSx2ZHYil2yuXWrQ"

        const val BASE_URL = "https://api.unsplash.com/"

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}