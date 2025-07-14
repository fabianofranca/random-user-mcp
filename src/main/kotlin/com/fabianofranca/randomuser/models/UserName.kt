package com.fabianofranca.randomuser.models

import kotlinx.serialization.Serializable

@Serializable
data class UserName(
    val title: String,
    val first: String,
    val last: String
)