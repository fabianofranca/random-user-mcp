package com.fabianofranca.randomuser.models

import kotlinx.serialization.Serializable

@Serializable
data class ImageToBase64Args(val url: String)
