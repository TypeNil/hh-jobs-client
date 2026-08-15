package com.github.typenil.hhjobs.data.vacancies.internal.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ExperienceDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String,
)

@Serializable
internal data class EmploymentDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String,
)

@Serializable
internal data class ScheduleDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String,
)

@Serializable
internal data class KeySkillDto(
    @SerialName("name")
    val name: String,
)

@Serializable
internal data class SnippetDto(
    @SerialName("requirement")
    val requirement: String? = null,
    @SerialName("responsibility")
    val responsibility: String? = null,
)

@Serializable
internal data class ContactsDto(
    @SerialName("name")
    val name: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("phones")
    val phones: List<PhoneDto> = emptyList(),
)

@Serializable
internal data class PhoneDto(
    @SerialName("country")
    val country: String? = null,
    @SerialName("city")
    val city: String? = null,
    @SerialName("number")
    val number: String? = null,
    @SerialName("comment")
    val comment: String? = null,
)
