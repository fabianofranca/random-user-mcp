package com.fabianofranca.randomuser.dsl

import io.modelcontextprotocol.kotlin.sdk.Tool
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

/**
 * DSL for creating schema properties based on MCP Supported Schema Types.
 * See: https://modelcontextprotocol.io/specification/2025-06-18/client/elicitation#supported-schema-types
 */

// Main function to create schema properties using the DSL
fun schemaProperties(init: SchemaPropertiesBuilder.() -> Unit): JsonObject {
    return SchemaPropertiesBuilder().apply(init).build()
}

class SchemaPropertiesBuilder {
    private val properties = mutableMapOf<String, JsonObject>()

    fun property(name: String, init: SchemaBuilder.() -> Unit) {
        properties[name] = SchemaBuilder().apply(init).build()
    }

    fun build(): JsonObject = buildJsonObject {
        properties.forEach { (name, schema) ->
            put(name, schema)
        }
    }
}

fun SchemaPropertiesBuilder.string(name: String, init: SchemaBuilder.() -> Unit = {}) {
    property(name) {
        type("string")
        init()
    }
}

fun SchemaPropertiesBuilder.number(name: String, init: SchemaBuilder.() -> Unit = {}) {
    property(name) {
        type("number")
        init()
    }
}

fun SchemaPropertiesBuilder.boolean(name: String, init: SchemaBuilder.() -> Unit = {}) {
    property(name) {
        type("boolean")
        init()
    }
}

fun SchemaPropertiesBuilder.objectProperty(name: String, init: SchemaBuilder.() -> Unit = {}) {
    property(name) {
        type("object")
        init()
    }
}

fun SchemaPropertiesBuilder.array(name: String, init: SchemaBuilder.() -> Unit = {}) {
    property(name) {
        type("array")
        init()
    }
}

class SchemaBuilder {
    private val properties = mutableMapOf<String, Any>()
    private var type: String? = null
    private var description: String? = null
    private var defaultValue: Any? = null

    fun type(value: String) {
        type = value
        properties["type"] = value
    }

    fun description(value: String) {
        description = value
        properties["description"] = value
    }

    fun default(value: Any) {
        defaultValue = value
        properties["default"] = value
    }

    fun build(): JsonObject = buildJsonObject {
        properties.forEach { (key, value) ->
            when (value) {
                is String -> put(key, value)
                is Number -> put(key, value)
                is Boolean -> put(key, value)
                else -> put(key, value.toString())
            }
        }
    }
}

/**
 * DSL for creating Tool.Input with schema properties
 */
class ToolInputBuilder {
    private var propertiesBuilder: SchemaPropertiesBuilder? = null
    private val requiredFields = mutableListOf<String>()

    fun properties(init: SchemaPropertiesBuilder.() -> Unit) {
        propertiesBuilder = SchemaPropertiesBuilder().apply(init)
    }

    fun required(vararg fields: String) {
        requiredFields.addAll(fields)
    }

    fun build(): Tool.Input {
        return Tool.Input(
            properties = propertiesBuilder?.build() ?: buildJsonObject {},
            required = requiredFields
        )
    }
}

fun toolInput(init: ToolInputBuilder.() -> Unit): Tool.Input {
    return ToolInputBuilder().apply(init).build()
}
