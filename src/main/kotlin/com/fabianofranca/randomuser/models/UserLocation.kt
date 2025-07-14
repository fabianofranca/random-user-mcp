package com.fabianofranca.randomuser.models

import kotlinx.serialization.Serializable

@Serializable
data class UserLocation(
    val street: UserStreet,
    val city: String,
    val state: String,
    val country: String,
    @Serializable(with = PostcodeSerializer::class)
    val postcode: String?,
    val coordinates: UserCoordinates,
    val timezone: UserTimezone
)
