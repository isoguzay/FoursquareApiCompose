package com.adyen.android.assignment.ui.screens.home.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.adyen.android.assignment.R
import com.adyen.android.assignment.ui.components.MapMarker
import com.adyen.android.assignment.ui.screens.home.viewmodel.HomeViewModel
import com.adyen.android.assignment.utils.Constant.GOOGLE_MAPS_CAMERA_ZOOM
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            homeViewModel.getDummyAmsterdamLocation(),
            GOOGLE_MAPS_CAMERA_ZOOM
        )
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        MapMarker(
            position = homeViewModel.getDummyAmsterdamLocation(),
            context = LocalContext.current,
            iconResourceId = R.drawable.ic_home,
            title = "title",
            snippet = "snippet"
        )
    }

}

