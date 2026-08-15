package com.github.typenil.hhjobs.data.vacancies.model

/**
 * Public domain model representing a paginated response of vacancies.
 */
data class VacanciesPage(
    val items: List<Vacancy>,
    val found: Int,
    val pages: Int,
    val page: Int,
    val perPage: Int,
)
