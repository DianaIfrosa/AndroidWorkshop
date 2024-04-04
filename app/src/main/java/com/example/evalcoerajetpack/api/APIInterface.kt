package com.example.evalcoerajetpack.api

import com.example.evalcoerajetpack.model.JSONResult
import retrofit2.Call
import retrofit2.http.GET


interface APIInterface {
    @GET("planets")
    fun getAllPlanets(): Call<JSONResult>
}