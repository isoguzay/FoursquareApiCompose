package com.adyen.android.assignment.usecase

import com.adyen.android.assignment.model.request.LocationRequestModel
import com.adyen.android.assignment.model.response.PlacesResponse
import com.adyen.android.assignment.network.util.NetworkResult

interface IGetPlaces {

    /**
     * Get places use case
     * @param requestModel
     */
    suspend operator fun invoke(requestModel: LocationRequestModel): NetworkResult<PlacesResponse>
    
}