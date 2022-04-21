package com.adyen.android.assignment.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adyen.android.assignment.repository.FakePlacesRepository
import com.adyen.android.assignment.util.CoroutinesTestRule
import com.adyen.android.assignment.util.FakeDataTest
import com.adyen.android.assignment.util.FakeDataTest.getDummyLocationRequest
import com.adyen.android.assignment.util.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = CoroutinesTestRule()

    private lateinit var fakePlacesRepository: FakePlacesRepository
    private lateinit var fakeHomeViewModel: FakeHomeViewModel

    @Before
    fun before() {
        fakePlacesRepository = FakePlacesRepository()
        fakeHomeViewModel = FakeHomeViewModel(fakePlacesRepository)
    }

    @Test
    fun verify_venue_list_when_service_failure_should_be_empty() {
        fakePlacesRepository.setNetworkResultStatus(networkResult = false)
        fakeHomeViewModel.getPlaces(requestModel = getDummyLocationRequest())
        val result = fakeHomeViewModel.venuesListState
        assertThat(result.venueList).isNull()
    }

    @Test
    fun verify_venue_list_when_service_success_should_not_be_null() {
        fakePlacesRepository.setNetworkResultStatus(networkResult = true)
        fakeHomeViewModel.getPlaces(requestModel = getDummyLocationRequest())
        val result = fakeHomeViewModel.venuesListState
        assertThat(result.venueList).isNotNull()
    }

    @Test
    fun verify_venue_error_state_when_service_failure_should_be_fail() {
        fakePlacesRepository.setNetworkResultStatus(networkResult = false)
        fakeHomeViewModel.getPlaces(requestModel = getDummyLocationRequest())
        val result = fakeHomeViewModel.venuesListState
        assertThat(result.error).isEqualTo("404")
    }

    @Test
    fun verify_venue_error_state_when_service_success_should_be_null() {
        fakePlacesRepository.setNetworkResultStatus(networkResult = true)
        fakeHomeViewModel.getPlaces(requestModel = getDummyLocationRequest())
        val result = fakeHomeViewModel.venuesListState
        assertThat(result.error).isNull()
    }

    @Test
    fun verify_venue_list_element_geocode_main_should_not_be_null() {
        fakePlacesRepository.setNetworkResultStatus(networkResult = true)
        fakeHomeViewModel.getPlaces(requestModel = getDummyLocationRequest())
        val result = fakeHomeViewModel.venuesListState
        assertThat(result.venueList?.get(0)?.geocodes?.main).isNotNull()
    }

    @Test
    fun verify_selected_place_name_should_not_be_null() {
        fakePlacesRepository.setNetworkResultStatus(networkResult = true)
        fakeHomeViewModel.setSelectedPlace(FakeDataTest.getSelectedPlace())
        val result = fakeHomeViewModel.selectedPlace.getOrAwaitValueTest()
        assertThat(result.name).isNotNull()
    }

    @Test
    fun verify_current_location_should_not_be_null() {
        fakePlacesRepository.setNetworkResultStatus(networkResult = true)
        fakeHomeViewModel.setCurrentLocation(getDummyLocationRequest())
        val result = fakeHomeViewModel.currentLocation.getOrAwaitValueTest()
        assertThat(result).isNotNull()
    }

}