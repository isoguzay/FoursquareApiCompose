package com.adyen.android.assignment.datasource.remote

import com.adyen.android.assignment.model.response.PlacesReponse
import com.adyen.android.assignment.model.request.LocationRequestModel
import com.adyen.android.assignment.network.querybuilder.VenueRecommendationsQueryBuilder
import com.adyen.android.assignment.network.service.PlacesService
import com.adyen.android.assignment.network.util.NetworkResult
import javax.inject.Inject

class PlacesRemoteData @Inject constructor(private val remoteService: PlacesService) {

    suspend fun getPlaces(requestModel: LocationRequestModel): NetworkResult<PlacesReponse> {
        return remoteService.getVenueRecommendationPlaces(
            VenueRecommendationsQueryBuilder()
                .setLatitudeLongitude(
                    latitude = requestModel.latitude,
                    longitude = requestModel.longitude
                ).build()
        )
    }

}