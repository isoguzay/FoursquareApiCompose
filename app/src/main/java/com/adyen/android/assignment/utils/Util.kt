package com.adyen.android.assignment.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

fun Context.hasPermission(permission: String): Boolean {
    if (permission == Manifest.permission.ACCESS_BACKGROUND_LOCATION &&
        Build.VERSION.SDK_INT < Build.VERSION_CODES.Q
    ) {
        return true
    }
    return ActivityCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_GRANTED
}

@RequiresApi(Build.VERSION_CODES.M)
fun Activity.requestPermissionWithRationale(
    permission: String,
    requestCode: Int
): Boolean {
    val provideRationale = shouldShowRequestPermissionRationale(permission)
    if (!provideRationale)
        requestPermissions(arrayOf(permission), requestCode)
    return provideRationale
}
