package com.example.evalcoerajetpack.model

import com.google.gson.annotations.SerializedName

data class JSONResult(
    val count: Int,
    val next: String,
    val previous: Any,
    @SerializedName("results")
    val planets: List<Planet>?
)