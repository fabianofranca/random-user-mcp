package com.fabianofranca.randomuser.models

import kotlinx.serialization.Serializable

@Serializable
data class UserStreet(
    val number: Int,
    val name: String
)