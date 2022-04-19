package com.adyen.android.assignment

import com.adyen.android.assignment.network.querybuilder.VenueRecommendationsQueryBuilder
import com.adyen.android.assignment.network.service.PlacesService
import org.junit.Assert.*
import org.junit.Test

class PlacesUnitTest {
    @Test
    fun testResponseCode() {
        val query = VenueRecommendationsQueryBuilder()
            .setLatitudeLongitude(52.376510, 4.905890)
            .build()
        val response = PlacesService.instance
            .getVenueRecommendations(query)
            .execute()

        val errorBody = response.errorBody()
        assertNull("Received an error: ${errorBody?.string()}", errorBody)

        val responseWrapper = response.body()
        assertNotNull("Response is null.", responseWrapper)
        assertEquals("Response code", 200, response.code())
    }
}
