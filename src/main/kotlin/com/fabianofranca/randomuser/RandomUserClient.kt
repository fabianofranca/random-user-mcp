package com.fabianofranca.randomuser

import com.fabianofranca.randomuser.models.RandomUserResponse

/**
 * Interface that defines methods to interact with the randomuser.me API.
 * This interface makes it easier to mock the client in unit tests.
 */
interface RandomUserClient {
    /**
     * Fetches users from the randomuser.me API.
     *
     * @param results The number of user results to fetch (default: 1)
     * @param page The page number for pagination (default: 1)
     * @param nationality The nationality code for filtering users (default: "us")
     * @return A RandomUserResponse containing the fetched user data
     */
    suspend fun getUsers(
        results: Int = DEFAULT_RESULTS,
        page: Int = DEFAULT_PAGE,
        nationality: String = DEFAULT_NATIONALITY
    ): RandomUserResponse

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
