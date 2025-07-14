package com.fabianofranca.randomuser.models

import kotlinx.serialization.Serializable

@Serializable
data class UserDob(
    val date: String,
    val age: Int
)