package com.fabianofranca.randomuser.utils

import com.fabianofranca.randomuser.RandomUserClient
import com.fabianofranca.randomuser.models.*

/**
 * Mock implementation of RandomUserClient for testing
 */
class MockRandomUserClient(
    private val mockResponse: RandomUserResponse? = null,
    private val throwException: Boolean = false
) : RandomUserClient {
    var capturedResults: Int? = null
    var capturedPage: Int? = null
    var capturedNationality: String? = null
    var capturedVersion: String? = null
    var capturedSeed: String? = null
    var capturedGender: String? = null
    var capturedPassword: String? = null
    var capturedInclude: String? = null
    var capturedExclude: String? = null

    // Public property to access the expected response
    val expectedResponse: RandomUserResponse
        get() = mockResponse ?: createDefaultMockResponse()

    override suspend fun getUsers(args: GetUsersArgs): RandomUserResponse {
        capturedResults = args.results
        capturedPage = args.page
        capturedNationality = args.nationality
        capturedVersion = args.version
        capturedSeed = args.seed
        capturedGender = args.gender
        capturedPassword = args.password
        capturedInclude = args.include
        capturedExclude = args.exclude

        if (throwException) {
            throw Exception("Test exception")
        }

        return expectedResponse
    }

    private fun createDefaultMockResponse(): RandomUserResponse {
        return RandomUserResponse(
            results = listOf(
                UserResult(
                    gender = "male",
                    name = UserName(title = "Mr", first = "John", last = "Doe"),
                    location = UserLocation(
                        street = UserStreet(number = 123, name = "Main St"),
                        city = "New York",
                        state = "NY",
                        country = "USA",
                        postcode = "10001",
                        coordinates = UserCoordinates(latitude = "40.7128", longitude = "-74.0060"),
                        timezone = UserTimezone(offset = "-5:00", description = "Eastern Time")
                    ),
                    email = "john.doe@example.com",
                    login = UserLogin(
                        uuid = "12345",
                        username = "johndoe",
                        password = "password",
                        salt = "salt",
                        md5 = "md5",
                        sha1 = "sha1",
                        sha256 = "sha256"
                    ),
                    dob = UserDob(date = "1990-01-01", age = 33),
                    registered = UserRegistered(date = "2010-01-01", age = 13),
                    phone = "123-456-7890",
                    cell = "098-765-4321",
                    id = UserId(name = "SSN", value = "123-45-6789"),
                    picture = UserPicture(
                        large = "https://example.com/large.jpg",
                        medium = "https://example.com/medium.jpg",
                        thumbnail = "https://example.com/thumbnail.jpg"
                    ),
                    nat = "US"
                )
            ),
            info = Info(
                seed = "test-seed",
                results = 1,
                page = 1,
                version = "1.4"
            )
        )
    }
}
