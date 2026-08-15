package com.github.typenil.hhjobs.data.vacancies.internal.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class VacanciesResponseDto(
    @SerialName("items")
    val items: List<VacancyItemDto> = emptyList(),
    @SerialName("found")
    val found: Int = 0,
    @SerialName("pages")
    val pages: Int = 0,
    @SerialName("page")
    val page: Int = 0,
    @SerialName("per_page")
    val perPage: Int = 20,
)
