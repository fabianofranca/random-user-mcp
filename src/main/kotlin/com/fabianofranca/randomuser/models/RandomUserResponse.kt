package com.fabianofranca.randomuser.models

import kotlinx.serialization.Serializable

@Serializable
data class RandomUserResponse(
    val results: List<UserResult>,
    val info: Info
)
