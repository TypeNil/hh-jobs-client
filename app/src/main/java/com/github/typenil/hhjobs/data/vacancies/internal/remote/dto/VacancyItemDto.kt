package com.github.typenil.hhjobs.data.vacancies.internal.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class VacancyItemDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("area")
    val area: AreaDto,
    @SerialName("salary")
    val salary: SalaryDto? = null,
    @SerialName("employer")
    val employer: EmployerDto,
    @SerialName("published_at")
    val publishedAt: String,
    @SerialName("snippet")
    val snippet: SnippetDto? = null,
    @SerialName("experience")
    val experience: ExperienceDto? = null,
    @SerialName("employment")
    val employment: EmploymentDto? = null,
    @SerialName("schedule")
    val schedule: ScheduleDto? = null,
    @SerialName("alternate_url")
    val alternateUrl: String? = null,
    @SerialName("has_test")
    val hasTest: Boolean = false,
)
