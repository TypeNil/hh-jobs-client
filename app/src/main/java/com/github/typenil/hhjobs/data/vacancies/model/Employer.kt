package com.github.typenil.hhjobs.data.vacancies.model

/**
 * Public domain model representing a hiring company/employer.
 */
data class Employer(
    val id: String?,
    val name: String,
    val logoUrl: String? = null,
    val alternateUrl: String? = null,
    val isTrusted: Boolean = false,
)
