package com.fabianofranca.randomuser.models

import kotlinx.serialization.Serializable

@Serializable
data class UserCoordinates(
    val latitude: String,
    val longitude: String
)