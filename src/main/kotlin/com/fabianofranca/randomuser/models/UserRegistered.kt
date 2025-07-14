package com.fabianofranca.randomuser.models

import kotlinx.serialization.Serializable

@Serializable
data class UserRegistered(
    val date: String,
    val age: Int
)