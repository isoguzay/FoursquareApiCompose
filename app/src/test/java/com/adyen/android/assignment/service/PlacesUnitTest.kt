package com.adyen.android.assignment.service

import com.adyen.android.assignment.network.querybuilder.VenueRecommendationsQueryBuilder
import com.adyen.android.assignment.network.service.PlacesService
import org.junit.Assert.*
import org.junit.Test

class PlacesUnitTest {

    @Test
    fun verify_api_service_return_error_body_is_null() {
        val query = VenueRecommendationsQueryBuilder()
            .setLatitudeLongitude(52.376510, 4.905890)
            .build()
        val response = PlacesService.instance
            .getVenueRecommendations(query)
            .execute()

        val errorBody = response.errorBody()
        assertNull("Received an error: ${errorBody?.string()}", errorBody)
    }

    @Test
    fun verify_api_service_return_response_is_null() {
        val query = VenueRecommendationsQueryBuilder()
            .setLatitudeLongitude(52.376510, 4.905890)
            .build()
        val response = PlacesService.instance
            .getVenueRecommendations(query)
            .execute()

        val responseWrapper = response.body()
        assertNotNull("Response is null.", responseWrapper)
    }

    @Test
    fun verify_api_service_return_response_code_success() {
        val query = VenueRecommendationsQueryBuilder()
            .setLatitudeLongitude(52.376510, 4.905890)
            .build()
        val response = PlacesService.instance
            .getVenueRecommendations(query)
            .execute()

        assertEquals("Response code", 200, response.code())
    }

}
