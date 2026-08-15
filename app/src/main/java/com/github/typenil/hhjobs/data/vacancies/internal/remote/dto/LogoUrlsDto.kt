package com.github.typenil.hhjobs.data.vacancies.internal.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class LogoUrlsDto(
    @SerialName("original")
    val original: String? = null,
    @SerialName("90")
    val size90: String? = null,
    @SerialName("240")
    val size240: String? = null,
)
