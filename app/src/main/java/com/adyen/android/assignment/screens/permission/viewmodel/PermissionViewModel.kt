package com.adyen.android.assignment.screens.permission.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adyen.android.assignment.screens.permission.types.LocationPermissionTypes
import javax.inject.Inject

class PermissionViewModel @Inject constructor() : ViewModel() {

    private val mLocationPermission = MutableLiveData<Boolean>()
    val locationPermission: LiveData<Boolean>
        get() = mLocationPermission

    var locationPermissionState = mutableStateOf(LocationPermissionTypes.REQUEST_AGAIN)

    /**
     * Set location permission state
     * @param state, true value for granted state
     */
    fun setLocationPermissionState(state: Boolean) {
        mLocationPermission.postValue(state)
    }

}