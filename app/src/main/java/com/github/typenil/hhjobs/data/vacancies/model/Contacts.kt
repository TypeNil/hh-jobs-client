package com.github.typenil.hhjobs.data.vacancies.model

/**
 * Public domain model representing employer contact information.
 */
data class Contacts(
    val name: String? = null,
    val email: String? = null,
    val phones: List<String> = emptyList(),
)
