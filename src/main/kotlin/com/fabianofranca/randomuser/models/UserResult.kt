package com.fabianofranca.randomuser.models

import kotlinx.serialization.Serializable

@Serializable
data class UserResult(
    val gender: String? = null,
    val name: UserName? = null,
    val location: UserLocation? = null,
    val email: String? = null,
    val login: UserLogin? = null,
    val dob: UserDob? = null,
    val registered: UserRegistered? = null,
    val phone: String? = null,
    val cell: String? = null,
    val id: UserId? = null,
    val picture: UserPicture? = null,
    val nat: String? = null
)
