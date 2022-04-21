package com.adyen.android.assignment.usecase

import com.adyen.android.assignment.model.request.LocationRequestModel
import com.adyen.android.assignment.model.response.PlacesResponse
import com.adyen.android.assignment.network.util.NetworkResult
import com.adyen.android.assignment.repository.remote.PlacesRepository
import javax.inject.Inject

class GetPlaces @Inject constructor(private val placesRepository: PlacesRepository) :
    IGetPlaces {

    /**
     * Get places use case
     * @param requestModel
     */
    override suspend operator fun invoke(requestModel: LocationRequestModel): NetworkResult<PlacesResponse> =
        placesRepository.getPlaces(requestModel)

}