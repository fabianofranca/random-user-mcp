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
    val nationality: String = DEFAULT_NATIONALITY,

    @SerialName(PARAM_VERSION)
    val version: String = DEFAULT_VERSION,

    @SerialName(PARAM_SEED)
    val seed: String? = null,

    @SerialName(PARAM_GENDER)
    val gender: String? = null
) {
    companion object {
        // API parameter names
        const val PARAM_RESULTS = "results"
        const val PARAM_PAGE = "page"
        const val PARAM_NATIONALITY = "nat"
        const val PARAM_VERSION = "version"
        const val PARAM_SEED = "seed"
        const val PARAM_GENDER = "gender"

        // Default values
        const val DEFAULT_RESULTS = 1
        const val DEFAULT_PAGE = 1
        const val DEFAULT_NATIONALITY = "us"
        const val DEFAULT_VERSION = "1.4" // Latest version as of implementation
    }
}
