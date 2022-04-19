package com.adyen.android.assignment.screens.home.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adyen.android.assignment.R
import com.adyen.android.assignment.model.response.Category
import com.adyen.android.assignment.model.response.Result
import com.adyen.android.assignment.network.util.NetworkResult
import com.adyen.android.assignment.screens.home.viewmodel.HomeViewModel
import com.adyen.android.assignment.screens.permission.screen.LocationPermissionScreen
import com.adyen.android.assignment.screens.permission.viewmodel.PermissionViewModel
import com.adyen.android.assignment.utils.Constant.GOOGLE_MAPS_CAMERA_ZOOM
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import timber.log.Timber

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun HomeScreen(
    locationRequestOnClick: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    permissionViewModel: PermissionViewModel = hiltViewModel()
) {

    val placesStatesList = mutableListOf<Result>()
    val showInfo = remember { mutableStateOf(false) }

    val placesState = homeViewModel.places.observeAsState()
    val selectedPlaceState = homeViewModel.selectedPlace.observeAsState()
    val permissionsState = permissionViewModel.locationPermission.observeAsState()
    val currentLocation = homeViewModel.currentLocation.observeAsState()
    val cameraPositionState = rememberCameraPositionState {}

    if (permissionsState.value == true) {
        LaunchedEffect(Unit) {
            homeViewModel.getPlaces(homeViewModel.getDummyLocationRequest())
            cameraPositionState.position = CameraPosition.fromLatLngZoom(
                homeViewModel.getDummyAmsterdamLocation(),
                GOOGLE_MAPS_CAMERA_ZOOM
            )
        }
    } else {
        LocationPermissionScreen()
    }

    when (val placesResponse = placesState.value) {
        is NetworkResult.Success -> {
            placesResponse.data?.results?.forEach { result ->
                placesStatesList.add(result)
            }
        }
        is NetworkResult.Failure -> {
            placesResponse.apply {
                Timber.e("$statusCode")
            }
        }
        else -> {}
    }

    if (placesStatesList.isNotEmpty()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            placesStatesList.forEach { place ->
                place.geocodes?.main?.let { main ->
                    Marker(
                        position = LatLng(
                            main.latitude!!,
                            main.longitude!!
                        ),
                        title = place.name,
                        snippet = place.location?.address,
                        onClick = {
                            homeViewModel.setSelectedPlace(place)
                            false
                        }
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Button(modifier = Modifier
                .wrapContentSize(),
                onClick = {
                    locationRequestOnClick()
                }) {
                Icon(
                    Icons.Default.Place,
                    contentDescription = "Show My Location"
                )
                Text(text = "My Location")
            }
        }
    }

    if (currentLocation.value != null) {
        homeViewModel.getPlaces(currentLocation.value!!)
        cameraPositionState.position = CameraPosition.fromLatLngZoom(
            homeViewModel.getUserCurrentLocation(),
            GOOGLE_MAPS_CAMERA_ZOOM
        )
    }

    if (selectedPlaceState.value != null) {
        val place = selectedPlaceState.value
        place?.let {
            showInfo.value = true
            SelectedPlaceInfo(showInfo, place)
        }
    }

}

@Composable
fun SelectedPlaceInfo(showInfo: MutableState<Boolean>, place: Result) {
    if (showInfo.value) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            Card(
                backgroundColor = MaterialTheme.colors.primary,
                modifier = Modifier.padding(
                    vertical = dimensionResource(id = R.dimen.place_info_card_vertical_padding),
                    horizontal = dimensionResource(id = R.dimen.place_info_card_horizontal_padding)
                )
            ) {
                var expanded by remember { mutableStateOf(false) }
                Row(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.place_info_row_padding))
                        .animateContentSize(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.place_info_column_padding))
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(Modifier.weight(3F)) {
                                SelectedPlaceNameField(name = place.name)
                            }
                            Row(Modifier.weight(1F)) {
                                IconButton(onClick = { expanded = !expanded }) {
                                    Icon(
                                        imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                                        contentDescription = ""
                                    )
                                }
                                IconButton(onClick = { showInfo.value = false }) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = ""
                                    )
                                }
                            }
                        }
                        if (expanded) {
                            SelectedPlaceAddressField(formatted_address = place.location?.formatted_address)
                            SelectedPlaceCategoriesField(categoryList = place.categories)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SelectedPlaceNameField(name: String?) {
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.place_info_spacer)))
    Icon(
        Icons.Default.Business,
        contentDescription = ""
    )
    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.place_info_spacer)))
    Text(text = "Name : $name")
}

@Composable
fun SelectedPlaceAddressField(formatted_address: String?) {
    if (!formatted_address.isNullOrEmpty()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                Icons.Default.Place,
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.place_info_spacer)))
            Text(text = "Address : $formatted_address")
        }
    }
}

@Composable
fun SelectedPlaceCategoriesField(categoryList: List<Category>?) {
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.place_info_spacer)))
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                Icons.Filled.Storefront,
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.place_info_spacer)))
            Text(
                text = "Categories",
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.place_info_spacer)))
        categoryList?.let { categories ->
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(count = categories.size, itemContent = { index ->
                    if (!categories[index].name.isNullOrEmpty()) {
                        CategoryChip(categories[index].name!!)
                    }
                })
            }
        }
    }
}

@Composable
fun CategoryChip(
    categoryName: String
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                vertical = 2.dp,
                horizontal = 4.dp
            )
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                color = Color.Transparent,
            )
            .padding(4.dp)
    ) {
        Text(
            text = categoryName,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(4.dp)
        )
    }
}




