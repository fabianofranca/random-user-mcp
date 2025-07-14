package com.fabianofranca.randomuser.models

import kotlinx.serialization.Serializable

@Serializable
data class UserId(
    val name: String,
    val value: String?
)