package com.fabianofranca.randomuser

import com.fabianofranca.randomuser.models.GetUsersArgs
import com.fabianofranca.randomuser.models.RandomUserResponse

/**
 * Interface that defines methods to interact with the randomuser.me API.
 * This interface makes it easier to mock the client in unit tests.
 */
interface RandomUserClient {
    /**
     * Fetches users from the randomuser.me API.
     *
     * @param args The arguments for the API request, including results, page, and nationality
     * @return A RandomUserResponse containing the fetched user data
     */
    suspend fun getUsers(args: GetUsersArgs = GetUsersArgs()): RandomUserResponse
}
