package com.adyen.android.assignment.datasource.remote

import com.adyen.android.assignment.model.request.LocationRequestModel
import com.adyen.android.assignment.model.response.PlacesResponse
import com.adyen.android.assignment.network.util.NetworkResult

interface IPlacesRemoteData {

    /**
     * Get places from service
     * @param requestModel
     */
    suspend fun getPlaces(requestModel: LocationRequestModel): NetworkResult<PlacesResponse>
    
}