package com.fabianofranca.randomuser.models

import kotlinx.serialization.Serializable

@Serializable
data class UserTimezone(
    val offset: String,
    val description: String
)