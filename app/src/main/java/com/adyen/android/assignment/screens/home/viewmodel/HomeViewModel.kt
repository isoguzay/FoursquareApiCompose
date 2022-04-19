package com.adyen.android.assignment.screens.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.model.request.LocationRequestModel
import com.adyen.android.assignment.model.response.PlacesReponse
import com.adyen.android.assignment.model.response.Result
import com.adyen.android.assignment.network.util.NetworkResult
import com.adyen.android.assignment.repository.remote.PlacesRepository
import com.adyen.android.assignment.utils.Constant
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val placesRepository: PlacesRepository) :
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
        when (val placesResponse = placesRepository.getPlaces(requestModel = requestModel)) {
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

    fun getUserCurrentLocation(): LatLng {
        return LatLng(currentLocation.value?.latitude!!, currentLocation.value?.longitude!!)
    }

    fun setCurrentLocation(locationRequestModel: LocationRequestModel) {
        mCurrentLocation.value = locationRequestModel
    }

    fun setCurrentLocationEmpty() {
        mCurrentLocation.value = null
    }

    fun getDummyAmsterdamLocation(): LatLng {
        return LatLng(Constant.DUMMY_LOCATION_LAT, Constant.DUMMY_LOCATION_LON)
    }

    fun getDummyLocationRequest(): LocationRequestModel {
        return LocationRequestModel(
            latitude = Constant.DUMMY_LOCATION_LAT,
            longitude = Constant.DUMMY_LOCATION_LON
        )
    }


}