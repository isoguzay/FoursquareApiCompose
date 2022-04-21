package com.adyen.android.assignment.repository.remote

import com.adyen.android.assignment.datasource.remote.PlacesRemoteData
import com.adyen.android.assignment.model.request.LocationRequestModel
import com.adyen.android.assignment.model.response.PlacesResponse
import com.adyen.android.assignment.network.util.NetworkResult
import javax.inject.Inject

class PlacesRepository @Inject constructor(private val remote: PlacesRemoteData) :
    IPlacesRepository {

    /**
     * Get places from service
     * @param requestModel
     */
    override suspend fun getPlaces(requestModel: LocationRequestModel): NetworkResult<PlacesResponse> {
        return remote.getPlaces(requestModel)
    }

}