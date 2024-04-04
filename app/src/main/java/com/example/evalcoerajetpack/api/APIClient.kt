package com.example.evalcoerajetpack.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class APIClient {

    // todo: implement a mechanism to retry the request to cover all pages
    companion object {
        private var retrofit: Retrofit? = null
        val BASE_URL = "https://swapi.dev/api/"

        fun getClient(): Retrofit? {
            val client = OkHttpClient.Builder().build()
            retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            return retrofit
    }
}
}