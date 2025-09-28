package com.example.lab_week_05.api

import com.example.lab_week_05.model.ImageData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Header

interface CatApiService {
    @GET("images/search")
    fun searchImages(
        @Query("limit") limit: Int,
        @Query("size") size: String,
        @Header("x-api-key") apiKey: String = "live_zO69leEKbR7ihKmIeDGXKOVRLOPMVz25n3es8IwRoh8EqIZVmhuIxeqokzQh9OQk"
    ): Call<List<ImageData>>
}
