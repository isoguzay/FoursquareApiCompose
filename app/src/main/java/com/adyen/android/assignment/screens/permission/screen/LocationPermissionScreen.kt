package com.adyen.android.assignment.screens.permission.screen

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.adyen.android.assignment.R
import com.adyen.android.assignment.screens.permission.types.LocationPermissionTypes
import com.adyen.android.assignment.screens.permission.viewmodel.PermissionViewModel
import com.adyen.android.assignment.ui.components.CustomImageFromResource
import com.adyen.android.assignment.utils.openAppPermissionSettings
import com.adyen.android.assignment.utils.openPermissionDialog

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun LocationPermissionScreen(permissionViewModel: PermissionViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.location_permission_screen_horizontal_padding)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        val activity = LocalContext.current as Activity

        when (permissionViewModel.locationPermissionState.value) {
            LocationPermissionTypes.REQUEST_AGAIN -> GetPermissionDialogAgain(
                activity = activity
            )
            LocationPermissionTypes.REQUEST_FROM_SETTINGS -> GetPermissionFromSettings(context = context)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun GetPermissionDialogAgain(
    activity: Activity
) {
    CustomImageFromResource(
        modifier = Modifier.wrapContentSize(),
        image = R.drawable.ic_places_splash_logo
    )
    Text(
        text = stringResource(id = R.string.get_location_permission_from_dialog_text),
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.location_permission_screen_spacer)))
    Button(onClick = {
        openPermissionDialog(activity)
    }) {
        Text(text = stringResource(id = R.string.get_location_permission_from_dialog_button_text))
    }
}

@Composable
fun GetPermissionFromSettings(
    context: Context
) {
    CustomImageFromResource(
        modifier = Modifier.wrapContentSize(),
        image = R.drawable.ic_places_splash_logo
    )
    Text(
        text = stringResource(id = R.string.get_location_permission_from_settings_text),
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.location_permission_screen_spacer)))
    Button(onClick = {
        openAppPermissionSettings(
            context = context
        )
    }) {
        Text(stringResource(id = R.string.get_location_permission_from_settings_button_text))
    }
}


