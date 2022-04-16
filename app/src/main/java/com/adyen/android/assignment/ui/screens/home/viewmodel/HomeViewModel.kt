package com.adyen.android.assignment.ui.screens.home.viewmodel

import androidx.lifecycle.ViewModel
import com.adyen.android.assignment.utils.Constant
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    fun getDummyAmsterdamLocation(): LatLng {
        return LatLng(Constant.DUMMY_LOCATION_LAT, Constant.DUMMY_LOCATION_LON)
    }
}