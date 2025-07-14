package com.fabianofranca.randomuser.models

import kotlinx.serialization.Serializable

@Serializable
data class UserPicture(
    val large: String,
    val medium: String,
    val thumbnail: String
)