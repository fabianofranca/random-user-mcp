package com.fabianofranca.randomuser

import com.fabianofranca.randomuser.models.UserLocation
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class UserLocationTest {

    @Test
    fun `test postcode deserialization with string value`() {
        testPostcodeDeserialization("XD1234", "XD1234")
    }

    @Test
    fun `test postcode deserialization with integer value`() {
        testPostcodeDeserialization(12345, "12345")
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
