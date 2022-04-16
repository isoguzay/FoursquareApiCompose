package com.adyen.android.assignment.api

class VenueRecommendationsQueryBuilder : PlacesQueryBuilder() {
    private var latitudeLongitude: String? = null

    fun setLatitudeLongitude(latitude: Double, longitude: Double): VenueRecommendationsQueryBuilder {
        this.latitudeLongitude = "$latitude,$longitude"
        return this
    }

    override fun putQueryParams(queryParams: MutableMap<String, String>) {
        latitudeLongitude?.apply { queryParams["ll"] = this }
    }
}
