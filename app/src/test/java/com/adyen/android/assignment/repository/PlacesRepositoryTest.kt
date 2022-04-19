package com.adyen.android.assignment.repository

import com.adyen.android.assignment.model.response.PlacesResponse
import com.adyen.android.assignment.network.util.NetworkResult
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class PlacesRepositoryTest {

    private lateinit var fakePlacesRepository: FakePlacesRepository

    @Before
    fun setUp() {
        fakePlacesRepository = FakePlacesRepository()
    }

    @Test
    fun verify_api_service_result_should_be_not_null() {
        runBlocking {
            val result = fakePlacesRepository.getPlacesOnLocation()
            assertThat(result).isNotNull()
        }
    }

    @Test
    fun verify_api_request_should_not_be_failure() {
        runBlocking {
            fakePlacesRepository.setNetworkResultStatus(true)
            val result = fakePlacesRepository.getPlacesOnLocation()
            assertThat(result).isNotInstanceOf(NetworkResult.Failure::class.java)
        }
    }

    @Test
    fun verify_api_request_result_should_be_instance_of_network_result_success() {
        runBlocking {
            fakePlacesRepository.setNetworkResultStatus(true)
            val result = fakePlacesRepository.getPlacesOnLocation()
            assertThat(result).isInstanceOf(NetworkResult.Success::class.java)
        }
    }

    @Test
    fun verify_api_request_result_data_should_not_be_null() {
        runBlocking {
            fakePlacesRepository.setNetworkResultStatus(true)
            val result = fakePlacesRepository.getPlacesOnLocation()
                    as NetworkResult.Success<PlacesResponse>
            assertThat(result.data).isNotNull()
        }
    }

    @Test
    fun verify_api_request_result_data_should_be_instance_of_places_response() {
        runBlocking {
            fakePlacesRepository.setNetworkResultStatus(true)
            val result = fakePlacesRepository.getPlacesOnLocation()
                    as NetworkResult.Success<PlacesResponse>
            assertThat(result.data).isInstanceOf(PlacesResponse::class.java)
        }
    }

    @Test
    fun verify_api_request_places_results_should_not_be_empty() {
        runBlocking {
            fakePlacesRepository.setNetworkResultStatus(true)
            val result = fakePlacesRepository.getPlacesOnLocation()
                    as NetworkResult.Success<PlacesResponse>
            assertThat(result.data?.results).isNotEmpty()
        }
    }

    @Test
    fun verify_api_request_return_failure_should_be_not_null() {
        runBlocking {
            fakePlacesRepository.setNetworkResultStatus(false)
            val result = fakePlacesRepository.getPlacesOnLocation()
                    as NetworkResult.Failure<PlacesResponse>
            assertThat(result).isNotNull()
        }
    }

    @Test
    fun verify_api_request_return_failure_should_be_instance_of_exception() {
        runBlocking {
            fakePlacesRepository.setNetworkResultStatus(false)
            val result = fakePlacesRepository.getPlacesOnLocation()
                    as NetworkResult.Failure<PlacesResponse>
            assertThat(result.exception).isInstanceOf(Exception::class.java)
        }
    }

}