package com.github.typenil.hhjobs.data.vacancies.model

/**
 * Public domain model representing a job vacancy in search results.
 */
data class Vacancy(
    val id: String,
    val name: String,
    val employer: Employer,
    val salary: Salary? = null,
    val area: Area,
    val experience: String? = null,
    val schedule: String? = null,
    val employment: String? = null,
    val snippetRequirement: String? = null,
    val snippetResponsibility: String? = null,
    val publishedAt: String = "",
    val alternateUrl: String? = null,
    val isFavorite: Boolean = false,
)
