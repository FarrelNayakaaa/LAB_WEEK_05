package com.example.lab_week_05

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.lab_week_05.api.CatApiService
import com.example.lab_week_05.model.ImageData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    // Retrofit dengan Moshi + KotlinJsonAdapterFactory
    private val retrofit by lazy {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    private val catApiService by lazy {
        retrofit.create(CatApiService::class.java)
    }

    private lateinit var apiResponseView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiResponseView = findViewById(R.id.api_response)

        getCatImageResponse()
    }

    private fun getCatImageResponse() {
        val call = catApiService.searchImages(1, "full")
        call.enqueue(object : Callback<List<ImageData>> {
            override fun onFailure(call: Call<List<ImageData>>, t: Throwable) {
                Log.e(MAIN_ACTIVITY, "Failed to get response", t)
                apiResponseView.text = "Error: ${t.message}"
            }

            override fun onResponse(
                call: Call<List<ImageData>>,
                response: Response<List<ImageData>>
            ) {
                if (response.isSuccessful) {
                    val images = response.body()
                    val firstImage = images?.firstOrNull()?.url

                    if (!firstImage.isNullOrEmpty()) {
                        apiResponseView.text = "Image URL: $firstImage"
                        Log.d(MAIN_ACTIVITY, "Image URL: $firstImage")
                    } else {
                        apiResponseView.text = "No URL found"
                        Log.w(MAIN_ACTIVITY, "Response without URL: $images")
                    }
                } else {
                    val errorMsg = response.errorBody()?.string().orEmpty()
                    Log.e(MAIN_ACTIVITY, "Failed response: $errorMsg")
                    apiResponseView.text = "Error: $errorMsg"
                }
            }
        })
    }

    companion object {
        const val MAIN_ACTIVITY = "MAIN_ACTIVITY"
    }
}

