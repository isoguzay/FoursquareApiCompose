package com.adyen.android.assignment.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.adyen.android.assignment.BuildConfig

/**
 * check hasPermission extension function
 */
fun Context.hasPermission(permission: String): Boolean {
    if (permission == Manifest.permission.ACCESS_BACKGROUND_LOCATION &&
        Build.VERSION.SDK_INT < Build.VERSION_CODES.Q
    ) {
        return true
    }
    return ActivityCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_GRANTED
}

/**
 * requestPermissionWithRationale extension function
 */
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

/**
 * Open application detail settings for getting permissions
 */
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

/**
 * Open permission dialog for getting permissions
 */
fun openPermissionDialog(activity: Activity) {
    val permission = Manifest.permission.ACCESS_FINE_LOCATION
    val requestCode = Constant.REQUEST_FINE_LOCATION_PERMISSIONS_REQUEST_CODE
    ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
}