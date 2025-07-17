package com.fabianofranca.randomuser.models

import kotlinx.serialization.Serializable

@Serializable
data class GetPictureInBase64Args(val url: String)
