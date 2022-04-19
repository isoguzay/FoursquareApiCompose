package com.adyen.android.assignment.repository

import com.adyen.android.assignment.model.request.LocationRequestModel
import com.adyen.android.assignment.model.response.PlacesResponse
import com.adyen.android.assignment.network.util.NetworkResult
import com.adyen.android.assignment.utils.FakeDataTest

class FakePlacesRepository {

    private var isNetworkResultSuccess = false

    fun setNetworkResultStatus(networkResult: Boolean) {
        isNetworkResultSuccess = networkResult
    }

    fun getPlacesOnLocation(requestModel: LocationRequestModel): NetworkResult<PlacesResponse> {
        return if (isNetworkResultSuccess)
            FakeDataTest.getPlacesFromServiceFakeResponse(requestModel)
        else
            FakeDataTest.getFakeFailureExceptionResponse(requestModel)
    }

}