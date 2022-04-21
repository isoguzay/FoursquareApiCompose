package com.adyen.android.assignment.repository.remote

import com.adyen.android.assignment.model.request.LocationRequestModel
import com.adyen.android.assignment.model.response.PlacesResponse
import com.adyen.android.assignment.network.util.NetworkResult

interface IPlacesRepository {

    /**
     * Get places from service
     * @param requestModel
     */
    suspend fun getPlaces(requestModel: LocationRequestModel): NetworkResult<PlacesResponse>

}