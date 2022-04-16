package com.adyen.android.assignment.api

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

abstract class PlacesQueryBuilder {

    fun build(): Map<String, String> {
        val queryParams = hashMapOf("v" to dateFormat.format(Date()))
        putQueryParams(queryParams)
        return queryParams
    }

    abstract fun putQueryParams(queryParams: MutableMap<String, String>)

    companion object {
        private val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.ROOT)
    }
}
