package com.github.typenil.hhjobs.data.vacancies.model

/**
 * Public domain model representing full vacancy details.
 */
data class VacancyDetails(
    val id: String,
    val name: String,
    val descriptionHtml: String,
    val keySkills: List<String> = emptyList(),
    val employer: Employer,
    val salary: Salary? = null,
    val area: Area,
    val experience: String? = null,
    val schedule: String? = null,
    val employment: String? = null,
    val publishedAt: String = "",
    val alternateUrl: String? = null,
    val contacts: Contacts? = null,
    val isFavorite: Boolean = false,
)
