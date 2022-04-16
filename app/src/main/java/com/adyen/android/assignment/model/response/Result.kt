package com.adyen.android.assignment.model.response

data class Result(
    var categories: List<Category>?,
    var chains: List<Any>?,
    var distance: Int?,
    var fsq_id: String?,
    var geocodes: Geocodes?,
    var location: Location?,
    var name: String?,
    var related_places: RelatedPlaces?,
    var timezone: String?
)