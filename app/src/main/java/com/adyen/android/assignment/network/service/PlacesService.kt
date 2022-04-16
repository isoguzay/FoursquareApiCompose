package com.adyen.android.assignment.network.service

import com.adyen.android.assignment.BuildConfig
import com.adyen.android.assignment.data.remote.model.ResponseWrapper
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap


interface PlacesService {
    /**
     * Get venue recommendations.
     *
     * See [the docs](https://developer.foursquare.com/reference/places-nearby)
     */
    @Headers("Authorization: ${BuildConfig.API_KEY}")
    @GET("places/nearby")
    fun getVenueRecommendations(@QueryMap query: Map<String, String>): Call<ResponseWrapper>

    companion object {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BuildConfig.FOURSQUARE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val instance: PlacesService by lazy { retrofit.create(PlacesService::class.java) }
    }
}
