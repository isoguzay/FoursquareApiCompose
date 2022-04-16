package com.adyen.android.assignment.data.remote.model

data class GeoCode(
    val main: Main
)

data class Main(
    val latitude: Double,
    val longitude: Double,
)