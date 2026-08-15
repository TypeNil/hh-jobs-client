package com.github.typenil.hhjobs.data.vacancies.internal.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class EmployerDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String,
    @SerialName("logo_urls")
    val logoUrls: LogoUrlsDto? = null,
    @SerialName("alternate_url")
    val alternateUrl: String? = null,
    @SerialName("trusted")
    val trusted: Boolean = false,
)
