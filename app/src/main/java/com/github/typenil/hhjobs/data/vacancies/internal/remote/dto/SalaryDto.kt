package com.github.typenil.hhjobs.data.vacancies.internal.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SalaryDto(
    @SerialName("from")
    val from: Int? = null,
    @SerialName("to")
    val to: Int? = null,
    @SerialName("currency")
    val currency: String? = null,
    @SerialName("gross")
    val gross: Boolean? = null,
)
