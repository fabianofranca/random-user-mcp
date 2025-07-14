package com.fabianofranca.randomuser.models

import kotlinx.serialization.Serializable

@Serializable
data class UserResult(
    val gender: String,
    val name: UserName,
    val location: UserLocation,
    val email: String,
    val login: UserLogin,
    val dob: UserDob,
    val registered: UserRegistered,
    val phone: String,
    val cell: String,
    val id: UserId,
    val picture: UserPicture,
    val nat: String
)
