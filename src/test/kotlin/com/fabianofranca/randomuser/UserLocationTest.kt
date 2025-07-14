package com.fabianofranca.randomuser

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class UserLocationTest {
    
    @Test
    fun `test postcode deserialization with string value`() {
        val json = """
            {
                "street": {
                    "number": 123,
                    "name": "Main St"
                },
                "city": "New York",
                "state": "NY",
                "country": "USA",
                "postcode": "XD1234",
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
        
        val location = Json.decodeFromString<UserLocation>(json)
        assertEquals("XD1234", location.postcode)
    }
    
    @Test
    fun `test postcode deserialization with integer value`() {
        val json = """
            {
                "street": {
                    "number": 123,
                    "name": "Main St"
                },
                "city": "New York",
                "state": "NY",
                "country": "USA",
                "postcode": 12345,
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
        
        val location = Json.decodeFromString<UserLocation>(json)
        assertEquals("12345", location.postcode)
    }
}