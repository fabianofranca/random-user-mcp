package com.fabianofranca.randomuser.dsl

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class DslTest {
    @Test
    fun `test schemaProperties creates JsonObject`() {
        // Given: A schema properties builder with a property
        // When: We build the schema properties
        val result = schemaProperties {
            property("testProperty") {
                type("string")
            }
        }

        // Then: The result should be a JsonObject with the expected property
        assertTrue(result is JsonObject)
        assertTrue(result.containsKey("testProperty"))
        assertEquals("string", result["testProperty"]?.jsonObject?.get("type")?.toString()?.replace("\"", ""))
    }

    @Test
    fun `test SchemaPropertiesBuilder adds properties correctly`() {
        // Given: A SchemaPropertiesBuilder
        val builder = SchemaPropertiesBuilder()

        // When: We add properties to it
        builder.property("prop1") {
            type("string")
        }
        builder.property("prop2") {
            type("number")
        }
        val result = builder.build()

        // Then: The result should contain both properties
        assertTrue(result.containsKey("prop1"))
        assertTrue(result.containsKey("prop2"))
        assertEquals("string", result["prop1"]?.jsonObject?.get("type")?.toString()?.replace("\"", ""))
        assertEquals("number", result["prop2"]?.jsonObject?.get("type")?.toString()?.replace("\"", ""))
    }

    @Test
    fun `test string property type`() {
        // Given: A SchemaPropertiesBuilder
        // When: We add a string property
        val result = schemaProperties {
            string("stringProp") {
                description("A string property")
                default("defaultValue")
            }
        }

        // Then: The property should have the correct type and attributes
        val prop = result["stringProp"]?.jsonObject
        assertNotNull(prop)
        assertEquals("string", prop["type"]?.toString()?.replace("\"", ""))
        assertEquals("A string property", prop["description"]?.toString()?.replace("\"", ""))
        assertEquals("defaultValue", prop["default"]?.toString()?.replace("\"", ""))
    }

    @Test
    fun `test number property type`() {
        // Given: A SchemaPropertiesBuilder
        // When: We add a number property
        val result = schemaProperties {
            number("numberProp") {
                description("A number property")
                default(42)
            }
        }

        // Then: The property should have the correct type and attributes
        val prop = result["numberProp"]?.jsonObject
        assertNotNull(prop)
        assertEquals("number", prop["type"]?.toString()?.replace("\"", ""))
        assertEquals("A number property", prop["description"]?.toString()?.replace("\"", ""))
        assertEquals("42", prop["default"]?.toString())
    }

    @Test
    fun `test boolean property type`() {
        // Given: A SchemaPropertiesBuilder
        // When: We add a boolean property
        val result = schemaProperties {
            boolean("boolProp") {
                description("A boolean property")
                default(true)
            }
        }

        // Then: The property should have the correct type and attributes
        val prop = result["boolProp"]?.jsonObject
        assertNotNull(prop)
        assertEquals("boolean", prop["type"]?.toString()?.replace("\"", ""))
        assertEquals("A boolean property", prop["description"]?.toString()?.replace("\"", ""))
        assertEquals("true", prop["default"]?.toString())
    }

    @Test
    fun `test object property type`() {
        // Given: A SchemaPropertiesBuilder
        // When: We add an object property
        val result = schemaProperties {
            objectProperty("objectProp") {
                description("An object property")
            }
        }

        // Then: The property should have the correct type and attributes
        val prop = result["objectProp"]?.jsonObject
        assertNotNull(prop)
        assertEquals("object", prop["type"]?.toString()?.replace("\"", ""))
        assertEquals("An object property", prop["description"]?.toString()?.replace("\"", ""))
    }

    @Test
    fun `test array property type`() {
        // Given: A SchemaPropertiesBuilder
        // When: We add an array property
        val result = schemaProperties {
            array("arrayProp") {
                description("An array property")
            }
        }

        // Then: The property should have the correct type and attributes
        val prop = result["arrayProp"]?.jsonObject
        assertNotNull(prop)
        assertEquals("array", prop["type"]?.toString()?.replace("\"", ""))
        assertEquals("An array property", prop["description"]?.toString()?.replace("\"", ""))
    }

    @Test
    fun `test SchemaBuilder builds correct JsonObject`() {
        // Given: A SchemaBuilder with various properties
        val builder = SchemaBuilder()

        // When: We add properties and build
        builder.type("string")
        builder.description("A test schema")
        builder.default("default value")
        val result = builder.build()

        // Then: The result should contain all properties
        assertEquals("string", result["type"]?.toString()?.replace("\"", ""))
        assertEquals("A test schema", result["description"]?.toString()?.replace("\"", ""))
        assertEquals("default value", result["default"]?.toString()?.replace("\"", ""))
    }

    @Test
    fun `test SchemaBuilder handles different value types`() {
        // Given: A SchemaBuilder
        val builder = SchemaBuilder()

        // When: We add properties of different types
        builder.type("object")
        builder.default(123)  // Number
        val result = builder.build()

        // Then: The result should handle the types correctly
        assertEquals("object", result["type"]?.toString()?.replace("\"", ""))
        assertEquals("123", result["default"]?.toString())
    }

    @Test
    fun `test ToolInputBuilder creates Tool Input with properties`() {
        // Given: A ToolInputBuilder with properties
        // When: We build the Tool.Input
        val input = toolInput {
            properties {
                string("name") {
                    description("User name")
                }
                number("age") {
                    description("User age")
                }
            }
            required("name")
        }

        // Then: The Tool.Input should have the correct properties and required fields
        val properties = input.properties
        assertTrue(properties.containsKey("name"))
        assertTrue(properties.containsKey("age"))
        assertEquals(listOf("name"), input.required)
    }

    @Test
    fun `test ToolInputBuilder with empty properties`() {
        // Given: A ToolInputBuilder with no properties
        // When: We build the Tool.Input
        val input = toolInput {
            required("id")
        }

        // Then: The Tool.Input should have empty properties and the required fields
        assertTrue(input.properties.toString() == "{}")
        assertEquals(listOf("id"), input.required)
    }

    @Test
    fun `test ToolInputBuilder with multiple required fields`() {
        // Given: A ToolInputBuilder with multiple required fields
        // When: We build the Tool.Input
        val input = toolInput {
            properties {
                string("name")
                number("age")
                boolean("active")
            }
            required("name", "age", "active")
        }

        // Then: The Tool.Input should have all the required fields
        assertEquals(listOf("name", "age", "active"), input.required)
    }

    @Test
    fun `test SchemaBuilder with custom object type`() {
        // Given: A SchemaBuilder
        val builder = SchemaBuilder()

        // When: We add a custom object that's not String, Number, or Boolean
        val customObject = object {
            override fun toString() = "custom object"
        }
        builder.default(customObject)
        val result = builder.build()

        // Then: The result should use toString() for the custom object
        assertEquals("custom object", result["default"]?.toString()?.replace("\"", ""))
    }

    @Test
    fun `test property type functions without init lambda`() {
        // Given: A SchemaPropertiesBuilder
        // When: We add properties without providing init lambdas
        val result = schemaProperties {
            string("stringProp")
            number("numberProp")
            boolean("boolProp")
            objectProperty("objectProp")
            array("arrayProp")
        }

        // Then: The properties should have the correct types
        assertEquals("string", result["stringProp"]?.jsonObject?.get("type")?.toString()?.replace("\"", ""))
        assertEquals("number", result["numberProp"]?.jsonObject?.get("type")?.toString()?.replace("\"", ""))
        assertEquals("boolean", result["boolProp"]?.jsonObject?.get("type")?.toString()?.replace("\"", ""))
        assertEquals("object", result["objectProp"]?.jsonObject?.get("type")?.toString()?.replace("\"", ""))
        assertEquals("array", result["arrayProp"]?.jsonObject?.get("type")?.toString()?.replace("\"", ""))
    }

    @Test
    fun `test ToolInputBuilder with no properties defined`() {
        // Given: A ToolInputBuilder with no properties defined
        // When: We build the Tool.Input
        val input = toolInput {
            // No properties defined, only required fields
            required("id")
        }

        // Then: The Tool.Input should have empty properties
        assertTrue(input.properties.toString() == "{}")
        assertEquals(listOf("id"), input.required)
    }
}
