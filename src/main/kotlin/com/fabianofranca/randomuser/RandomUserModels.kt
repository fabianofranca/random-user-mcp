package com.fabianofranca.randomuser

import kotlinx.serialization.Serializable

@Serializable
data class RandomUserResponse(
    val results: List<UserResult>,
    val info: Info
)

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

@Serializable
data class UserName(
    val title: String,
    val first: String,
    val last: String
)

@Serializable
data class UserLocation(
    val street: UserStreet,
    val city: String,
    val state: String,
    val country: String,
    val postcode: Long?,
    val coordinates: UserCoordinates,
    val timezone: UserTimezone
)

@Serializable
data class UserStreet(
    val number: Int,
    val name: String
)

@Serializable
data class UserCoordinates(
    val latitude: String,
    val longitude: String
)

@Serializable
data class UserTimezone(
    val offset: String,
    val description: String
)

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

@Serializable
data class UserDob(
    val date: String,
    val age: Int
)

@Serializable
data class UserRegistered(
    val date: String,
    val age: Int
)

@Serializable
data class UserId(
    val name: String,
    val value: String?
)

@Serializable
data class UserPicture(
    val large: String,
    val medium: String,
    val thumbnail: String
)

@Serializable
data class Info(
    val seed: String,
    val results: Int,
    val page: Int,
    val version: String
)
