package com.adyen.android.assignment.screens.home.viewmodel

import com.adyen.android.assignment.model.response.Result

data class VenuesListState(
    val venueList: List<Result>? = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)