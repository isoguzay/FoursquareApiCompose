package com.adyen.android.assignment.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.adyen.android.assignment.model.request.LocationRequestModel
import com.adyen.android.assignment.screens.home.view.HomeScreen
import com.adyen.android.assignment.screens.home.viewmodel.HomeViewModel
import com.adyen.android.assignment.screens.permission.types.LocationPermissionTypes
import com.adyen.android.assignment.screens.permission.viewmodel.PermissionViewModel
import com.adyen.android.assignment.ui.theme.AdyenApplicationTheme
import com.adyen.android.assignment.utils.Constant.REQUEST_FINE_LOCATION_PERMISSIONS_REQUEST_CODE
import com.adyen.android.assignment.utils.hasPermission
import com.adyen.android.assignment.utils.requestPermissionWithRationale
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val permissionViewModel: PermissionViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(applicationContext)
    }

    private var cancellationTokenSource = CancellationTokenSource()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var keepSplashScreen = true
        getPermission()

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                keepSplashScreen
            }
        }

        lifecycleScope.launchWhenCreated {
            delay(2000)
            keepSplashScreen = false
            setContent {
                AdyenApplicationTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        HomeScreen({ locationRequestOnClick() })
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancellationTokenSource.cancel()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()
        getPermission()
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_FINE_LOCATION_PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() ->
                    Timber.d("User interaction was cancelled.")
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    permissionViewModel.setLocationPermissionState(true)
                }
                else -> {
                    permissionViewModel.locationPermissionState.value =
                        LocationPermissionTypes.REQUEST_FROM_SETTINGS
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getPermission() {
        val permissionState = requestPermissionWithRationale(
            Manifest.permission.ACCESS_FINE_LOCATION,
            REQUEST_FINE_LOCATION_PERMISSIONS_REQUEST_CODE
        )
        if (permissionState)
            permissionViewModel.locationPermissionState.value =
                LocationPermissionTypes.REQUEST_AGAIN
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun locationRequestOnClick() {
        Timber.d("locationRequestOnClick()")
        val permissionApproved =
            applicationContext.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionApproved) {
            requestCurrentLocation()
        } else {
            permissionViewModel.locationPermissionState.value =
                LocationPermissionTypes.REQUEST_AGAIN
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestCurrentLocation() {
        if (applicationContext.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            val currentLocationTask: Task<Location> = fusedLocationClient.getCurrentLocation(
                LocationRequest.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            )
            currentLocationTask.addOnCompleteListener { task: Task<Location> ->
                if (task.isSuccessful && task.result != null) {
                    val result: Location = task.result
                    Log.d(
                        TAG,
                        "getCurrentLocation() (success): ${result.latitude}, ${result.longitude}"
                    )
                    homeViewModel.setCurrentLocation(
                        LocationRequestModel(
                            latitude = task.result.latitude,
                            longitude = task.result.longitude
                        )
                    )
                } else {
                    val exception = task.exception
                    Log.d(TAG, "getCurrentLocation() (failure): $exception\"")

                }
                Log.d(TAG, "getCurrentLocation() result: ${task.result.longitude}")

            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
