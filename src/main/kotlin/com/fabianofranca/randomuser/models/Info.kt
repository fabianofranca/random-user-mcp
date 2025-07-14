package com.fabianofranca.randomuser.models

import kotlinx.serialization.Serializable

@Serializable
data class Info(
    val seed: String,
    val results: Int,
    val page: Int,
    val version: String
)