package com.adyen.android.assignment.screens.home.viewmodel

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

    private val mGetPlaces = MutableLiveData<NetworkResult<PlacesReponse>>()
    val places: LiveData<NetworkResult<PlacesReponse>>
        get() = mGetPlaces

    private val mSelectedPlace = MutableLiveData<Result>()
    val selectedPlace: LiveData<Result>
        get() = mSelectedPlace

    fun getPlaces(requestModel: LocationRequestModel) = viewModelScope.launch {
        mGetPlaces.value = placesRepository.getPlaces(requestModel = requestModel)
    }

    fun getDummyAmsterdamLocation(): LatLng {
        return LatLng(Constant.DUMMY_LOCATION_LAT, Constant.DUMMY_LOCATION_LON)
    }

    fun setSelectedPlace(place: Result) {
        mSelectedPlace.value = place
    }

    fun getDummyLocationRequest(): LocationRequestModel {
        return LocationRequestModel(
            latitude = Constant.DUMMY_LOCATION_LAT,
            longitude = Constant.DUMMY_LOCATION_LON
        )
    }

}