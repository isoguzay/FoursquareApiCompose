package com.adyen.android.assignment.repository.remote

import com.adyen.android.assignment.datasource.remote.PlacesRemoteData
import com.adyen.android.assignment.model.response.PlacesReponse
import com.adyen.android.assignment.model.request.LocationRequestModel
import com.adyen.android.assignment.model.response.Result
import com.adyen.android.assignment.network.util.NetworkResult
import javax.inject.Inject

class PlacesRepository @Inject constructor(private val remote: PlacesRemoteData) {

    suspend fun getPlaces(requestModel: LocationRequestModel): NetworkResult<PlacesReponse> {
        return remote.getPlaces(requestModel)
    }

}