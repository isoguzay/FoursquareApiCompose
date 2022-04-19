package com.adyen.android.assignment.repository

import com.adyen.android.assignment.model.response.PlacesResponse
import com.adyen.android.assignment.network.util.NetworkResult
import com.adyen.android.assignment.utils.FakeDataTest

class FakePlacesRepository {

    private var isNetworkResultSuccess = false

    fun setNetworkResultStatus(value: Boolean) {
        isNetworkResultSuccess = value
    }

    fun getPlacesOnLocation(): NetworkResult<PlacesResponse> {
        return if (isNetworkResultSuccess)
            FakeDataTest.getPlacesFromServiceFakeResponse()
        else
            FakeDataTest.getFakeFailureExceptionResponse()
    }
    
}