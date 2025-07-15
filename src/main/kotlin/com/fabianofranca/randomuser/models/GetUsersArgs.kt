package com.fabianofranca.randomuser.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class representing the arguments used by GetUsers.
 * This class is used to deserialize arguments from CallToolRequest.
 */
@Serializable
data class GetUsersArgs(
    @SerialName(PARAM_RESULTS)
    val results: Int = DEFAULT_RESULTS,

    @SerialName(PARAM_PAGE)
    val page: Int = DEFAULT_PAGE,

    @SerialName(PARAM_NATIONALITY)
    val nationality: String = DEFAULT_NATIONALITY
) {
    companion object {
        // API parameter names
        const val PARAM_RESULTS = "results"
        const val PARAM_PAGE = "page"
        const val PARAM_NATIONALITY = "nat"

        // Default values
        const val DEFAULT_RESULTS = 1
        const val DEFAULT_PAGE = 1
        const val DEFAULT_NATIONALITY = "us"
    }
}
