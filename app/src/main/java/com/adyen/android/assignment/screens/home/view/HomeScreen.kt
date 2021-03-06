package com.adyen.android.assignment.screens.home.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adyen.android.assignment.R
import com.adyen.android.assignment.model.response.Category
import com.adyen.android.assignment.model.response.Result
import com.adyen.android.assignment.screens.home.viewmodel.HomeViewModel
import com.adyen.android.assignment.ui.components.CategoryChip
import com.adyen.android.assignment.util.Constant.GOOGLE_MAPS_CAMERA_ZOOM
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun HomeScreen(
    locationRequestOnClick: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val showProgress = remember { mutableStateOf(false) }
    val showInfo = remember { mutableStateOf(false) }
    val cameraPositionState = rememberCameraPositionState {}
    val venuesListState = homeViewModel.venuesListState
    val selectedPlaceState = homeViewModel.selectedPlace.observeAsState()
    val currentLocation = homeViewModel.currentLocation.observeAsState()

    LaunchedEffect(Unit) {
        homeViewModel.getPlaces(homeViewModel.getDummyLocationRequest())
        cameraPositionState.position = CameraPosition.fromLatLngZoom(
            homeViewModel.getDummyAmsterdamLocation(),
            GOOGLE_MAPS_CAMERA_ZOOM
        )
    }

    if (venuesListState.venueList?.isNotEmpty() == true) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            venuesListState.venueList.forEach { place ->
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
                .padding(all = dimensionResource(id = R.dimen.home_screen_show_my_current_location_box_padding)),
            contentAlignment = Alignment.BottomStart
        ) {
            Button(modifier = Modifier
                .wrapContentSize(),
                onClick = {
                    showProgress.value = true
                    locationRequestOnClick()
                }) {
                Icon(
                    Icons.Default.Place,
                    contentDescription = stringResource(id = R.string.home_screen_show_my_current_location_button_content_description)
                )
                Text(text = stringResource(id = R.string.home_screen_show_my_current_location_button_text))
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (venuesListState.isLoading) {
            CircularProgressIndicator()
        } else if (venuesListState.error != null) {
            Text(
                text = venuesListState.error,
                color = MaterialTheme.colors.error
            )
        }
    }

    if (currentLocation.value != null) {
        homeViewModel.getPlaces(currentLocation.value!!)
        cameraPositionState.position = CameraPosition.fromLatLngZoom(
            homeViewModel.getUserCurrentLocation(),
            GOOGLE_MAPS_CAMERA_ZOOM
        )
        showProgress.value = false
        homeViewModel.setCurrentLocationEmpty()
    }

    if (showProgress.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
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
                                        contentDescription = stringResource(id = R.string.home_screen_place_detail_expand_content_description)
                                    )
                                }
                                IconButton(onClick = { showInfo.value = false }) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = stringResource(id = R.string.home_screen_place_detail_close_content_description)
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
        contentDescription = stringResource(id = R.string.home_screen_place_detail_name_content_description)
    )
    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.place_info_spacer)))
    Text(text = stringResource(id = R.string.home_screen_place_detail_name) + " $name")
}

@Composable
fun SelectedPlaceAddressField(formatted_address: String?) {
    if (!formatted_address.isNullOrEmpty()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                Icons.Default.Place,
                contentDescription = stringResource(id = R.string.home_screen_place_detail_address_content_description)
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.place_info_spacer)))
            Text(text = stringResource(id = R.string.home_screen_place_detail_address) + " $formatted_address")
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
                contentDescription = stringResource(id = R.string.home_screen_place_detail_categories_content_description)
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.place_info_spacer)))
            Text(
                text = stringResource(id = R.string.home_screen_place_detail_categories),
                fontSize = dimensionResource(id = R.dimen.place_info_categories_text_size).value.sp
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





