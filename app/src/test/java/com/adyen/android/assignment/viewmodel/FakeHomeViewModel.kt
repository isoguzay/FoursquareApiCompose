package com.adyen.android.assignment.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.model.request.LocationRequestModel
import com.adyen.android.assignment.model.response.Result
import com.adyen.android.assignment.network.util.NetworkResult
import com.adyen.android.assignment.repository.FakePlacesRepository
import com.adyen.android.assignment.screens.home.viewmodel.VenuesListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FakeHomeViewModel @Inject constructor(private val fakePlacesRepository: FakePlacesRepository) :
    ViewModel() {

    var venuesListState by mutableStateOf(VenuesListState())

    private val mSelectedPlace = MutableLiveData<Result>()
    val selectedPlace: LiveData<Result>
        get() = mSelectedPlace

    private val mCurrentLocation = MutableLiveData<LocationRequestModel?>()
    val currentLocation: LiveData<LocationRequestModel?>
        get() = mCurrentLocation

    fun getPlaces(requestModel: LocationRequestModel) = viewModelScope.launch {
        venuesListState = venuesListState.copy(isLoading = true)
        when (val placesResponse =
            fakePlacesRepository.getPlacesOnLocation(requestModel = requestModel)) {
            is NetworkResult.Success -> {
                placesResponse.data?.results?.let { venueList ->
                    venuesListState = venuesListState.copy(
                        venueList = venueList,
                        isLoading = false,
                        error = null
                    )
                }
            }
            is NetworkResult.Failure -> {
                venuesListState = venuesListState.copy(
                    venueList = null,
                    isLoading = false,
                    error = placesResponse.statusCode.toString()
                )
            }
            else -> {}
        }
    }

    fun setSelectedPlace(place: Result) {
        mSelectedPlace.value = place
    }

    fun setCurrentLocation(locationRequestModel: LocationRequestModel) {
        mCurrentLocation.value = locationRequestModel
    }

}