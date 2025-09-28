package com.example.lab_week_05.model

import com.squareup.moshi.Json

data class ImageData(
    val id: String?,    //nullable
    @field:Json(name = "url") val url: String?,   //JSON match
    val width: Int? = null,
    val height: Int? = null,
    val breeds: List<CatBreedData>? = emptyList()
)
