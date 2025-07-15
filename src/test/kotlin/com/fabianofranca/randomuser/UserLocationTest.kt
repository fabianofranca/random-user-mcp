package com.fabianofranca.randomuser

import com.fabianofranca.randomuser.models.UserCoordinates
import com.fabianofranca.randomuser.models.UserLocation
import com.fabianofranca.randomuser.models.UserStreet
import com.fabianofranca.randomuser.models.UserTimezone
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class UserLocationTest {

    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `test postcode deserialization with string value`() {
        testPostcodeDeserialization("XD1234", "XD1234")
    }

    @Test
    fun `test postcode deserialization with integer value`() {
        testPostcodeDeserialization(12345, "12345")
    }

    @Test
    fun `test complete deserialization of UserLocation`() {
        // Given: A JSON string with all UserLocation fields
        val jsonString = """
            {
                "street": {
                    "number": 123,
                    "name": "Main St"
                },
                "city": "New York",
                "state": "NY",
                "country": "USA",
                "postcode": "10001",
                "coordinates": {
                    "latitude": "40.7128",
                    "longitude": "-74.0060"
                },
                "timezone": {
                    "offset": "-5:00",
                    "description": "Eastern Time (US & Canada)"
                }
            }
        """.trimIndent()

        // When: We deserialize the JSON to a UserLocation object
        val location = json.decodeFromString<UserLocation>(jsonString)

        // Then: All fields should be correctly deserialized
        assertEquals(123, location.street.number)
        assertEquals("Main St", location.street.name)
        assertEquals("New York", location.city)
        assertEquals("NY", location.state)
        assertEquals("USA", location.country)
        assertEquals("10001", location.postcode)
        assertEquals("40.7128", location.coordinates.latitude)
        assertEquals("-74.0060", location.coordinates.longitude)
        assertEquals("-5:00", location.timezone.offset)
        assertEquals("Eastern Time (US & Canada)", location.timezone.description)
    }

    @Test
    fun `test serialization of UserLocation`() {
        // Given: A UserLocation object
        val location = UserLocation(
            street = UserStreet(number = 123, name = "Main St"),
            city = "New York",
            state = "NY",
            country = "USA",
            postcode = "10001",
            coordinates = UserCoordinates(latitude = "40.7128", longitude = "-74.0060"),
            timezone = UserTimezone(offset = "-5:00", description = "Eastern Time (US & Canada)")
        )

        // When: We serialize the UserLocation object to JSON
        val jsonString = json.encodeToString(location)

        // Then: The JSON should contain all the expected fields with correct values
        val deserializedLocation = json.decodeFromString<UserLocation>(jsonString)
        assertEquals(location, deserializedLocation)
    }

    @Test
    fun `test null postcode handling`() {
        // Given: A JSON string with a null postcode
        val jsonString = """
            {
                "street": {
                    "number": 123,
                    "name": "Main St"
                },
                "city": "New York",
                "state": "NY",
                "country": "USA",
                "postcode": null,
                "coordinates": {
                    "latitude": "40.7128",
                    "longitude": "-74.0060"
                },
                "timezone": {
                    "offset": "-5:00",
                    "description": "Eastern Time (US & Canada)"
                }
            }
        """.trimIndent()

        // When: We deserialize the JSON to a UserLocation object
        val location = json.decodeFromString<UserLocation>(jsonString)

        // Then: The postcode should be null
        assertEquals(null, location.postcode)
    }

    /**
     * Helper method to test postcode deserialization with different value types
     * 
     * @param postcodeValue The postcode value to use in the JSON (can be String or Int)
     * @param expectedPostcode The expected postcode value after deserialization
     */
    private fun testPostcodeDeserialization(postcodeValue: Any, expectedPostcode: String) {
        // Given: A JSON string with a postcode value
        val json = """
            {
                "street": {
                    "number": 123,
                    "name": "Main St"
                },
                "city": "New York",
                "state": "NY",
                "country": "USA",
                "postcode": $postcodeValue,
                "coordinates": {
                    "latitude": "40.7128",
                    "longitude": "-74.0060"
                },
                "timezone": {
                    "offset": "-5:00",
                    "description": "Eastern Time (US & Canada)"
                }
            }
        """.trimIndent()

        // When: We deserialize the JSON to a UserLocation object
        val location = Json.decodeFromString<UserLocation>(json)

        // Then: The postcode should be correctly deserialized as a string
        assertEquals(expectedPostcode, location.postcode)
    }
}
