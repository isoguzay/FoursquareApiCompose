package com.adyen.android.assignment.screens.permission.screen

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.hilt.navigation.compose.hiltViewModel
import com.adyen.android.assignment.BuildConfig
import com.adyen.android.assignment.R
import com.adyen.android.assignment.screens.permission.types.LocationPermissionTypes
import com.adyen.android.assignment.screens.permission.viewmodel.PermissionViewModel
import com.adyen.android.assignment.ui.components.CustomImageFromResource
import com.adyen.android.assignment.utils.Constant.REQUEST_FINE_LOCATION_PERMISSIONS_REQUEST_CODE

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun LocationPermissionScreen(permissionViewModel: PermissionViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
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
        text = "Application needs location permission, please give location permission",
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(8.dp))
    Button(onClick = {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        val requestCode = REQUEST_FINE_LOCATION_PERMISSIONS_REQUEST_CODE
        requestPermissions(activity, arrayOf(permission), requestCode)
    }) {
        Text("Permission")
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
        text = "Application needs location permission, please go to settings then give location permission",
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(8.dp))
    Button(onClick = {
        openAppPermissionSettings(
            context = context
        )
    }) {
        Text("Go to Settings")
    }
}

fun openAppPermissionSettings(context: Context) {
    val intent = Intent()
    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    val uri = Uri.fromParts(
        "package",
        BuildConfig.APPLICATION_ID,
        null
    )
    intent.data = uri
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
}
