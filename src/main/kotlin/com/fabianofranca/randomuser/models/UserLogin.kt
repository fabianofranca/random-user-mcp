package com.fabianofranca.randomuser.models

import kotlinx.serialization.Serializable

@Serializable
data class UserLogin(
    val uuid: String,
    val username: String,
    val password: String,
    val salt: String,
    val md5: String,
    val sha1: String,
    val sha256: String
)