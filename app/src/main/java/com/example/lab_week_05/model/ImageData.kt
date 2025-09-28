package com.example.lab_week_05.model

import com.squareup.moshi.Json

data class ImageData(
    val id: String?,
    @field:Json(name = "url") val url: String?,
    val width: Int? = null,
    val height: Int? = null,
    val breeds: List<CatBreedData>? = emptyList()
)
