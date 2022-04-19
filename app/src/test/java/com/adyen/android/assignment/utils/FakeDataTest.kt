package com.adyen.android.assignment.utils

import android.content.res.Resources
import com.adyen.android.assignment.model.request.LocationRequestModel
import com.adyen.android.assignment.model.response.*
import com.adyen.android.assignment.network.util.NetworkResult
import com.google.android.gms.maps.model.LatLng

object FakeDataTest {

    private fun getFakePlacesList() = listOf(
        Result(
            categories = listOf(Category(icon = null, id = 0, name = "")),
            chains = null,
            distance = 5,
            geocodes = Geocodes(
                main = Main(latitude = 52.376513, longitude = 4.906092),
                roof = null
            ),
            location = Location(
                address = "Simon Carmiggeltstraat 6",
                country = "NL",
                cross_street = null,
                formatted_address = "Simon Carmiggeltstraat 6",
                postcode = null,
                locality = null,
                neighborhood = null,
                region = null
            ),
            name = "VodafoneZiggo Amsterdam (Oosterdokseiland)",
            related_places = null,
            timezone = "Europe/Amsterdam",
            fsq_id = "58a403d20593e16c4579149d"
        ),
        Result(
            categories = listOf(Category(icon = null, id = 0, name = "")),
            chains = null,
            distance = 127,
            geocodes = Geocodes(
                main = Main(latitude = 52.375978, longitude = 4.907301),
                roof = null
            ),
            location = Location(
                address = "Oosterdokskade 133",
                country = "NL",
                cross_street = null,
                formatted_address = "Oosterdokskade 133, 1011 DL Amsterdam",
                postcode = null,
                locality = null,
                neighborhood = null,
                region = null
            ),
            name = "Mo-Jo Japanese Kitchen",
            related_places = null,
            timezone = "North Holland",
            fsq_id = "5650bd5e498e58a4af099b37"
        ),
        Result(
            categories = listOf(Category(icon = null, id = 0, name = "")),
            chains = null,
            distance = 5,
            geocodes = Geocodes(
                main = Main(latitude = 52.376898, longitude = 4.904513),
                roof = null
            ),
            location = Location(
                address = "Oosterdoksstraat 4",
                country = "NL",
                cross_street = null,
                formatted_address = "Oosterdoksstraat 4 (Double Tree by Hilton), 1001 RD Amsterdam",
                postcode = "1001 RD",
                locality = null,
                neighborhood = null,
                region = null
            ),
            name = "Starbucks",
            related_places = null,
            timezone = "North Holland",
            fsq_id = "50056329e4b0c7e958812543"
        )
    )

    fun getSelectedPlace() = Result(
        categories = listOf(Category(icon = null, id = 0, name = "")),
        chains = null,
        distance = 5,
        geocodes = Geocodes(
            main = Main(latitude = 52.376898, longitude = 4.904513),
            roof = null
        ),
        location = Location(
            address = "Oosterdoksstraat 4",
            country = "NL",
            cross_street = null,
            formatted_address = "Oosterdoksstraat 4 (Double Tree by Hilton), 1001 RD Amsterdam",
            postcode = "1001 RD",
            locality = null,
            neighborhood = null,
            region = null
        ),
        name = "Starbucks",
        related_places = null,
        timezone = "North Holland",
        fsq_id = "50056329e4b0c7e958812543"
    )

    fun getPlacesFromServiceFakeResponse(requestModel: LocationRequestModel): NetworkResult<PlacesResponse> {
        return NetworkResult.Success(PlacesResponse(getFakePlacesList()))
    }

    fun getFakeFailureExceptionResponse(requestModel: LocationRequestModel): NetworkResult<PlacesResponse> {
        return NetworkResult.Failure(
            404, Resources.NotFoundException()
        )
    }

    fun getDummyAmsterdamLocation(): LatLng {
        return LatLng(Constant.DUMMY_LOCATION_LAT, Constant.DUMMY_LOCATION_LON)
    }

    fun getDummyLocationRequest(): LocationRequestModel {
        return LocationRequestModel(
            latitude = Constant.DUMMY_LOCATION_LAT,
            longitude = Constant.DUMMY_LOCATION_LON
        )
    }

}


