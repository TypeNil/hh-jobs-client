package com.github.typenil.hhjobs.data.vacancies

import com.github.typenil.hhjobs.data.vacancies.model.VacanciesPage
import com.github.typenil.hhjobs.data.vacancies.model.VacancyDetails

/**
 * Public contract / entry point for accessing vacancy data.
 * Features only interact with this interface.
 */
interface VacancyRepository {

    suspend fun searchVacancies(
        query: String? = null,
        area: String? = null,
        page: Int = 0,
        perPage: Int = 20,
        salary: Int? = null,
        onlyWithSalary: Boolean? = null,
        experience: String? = null,
        employment: String? = null,
        schedule: String? = null,
        orderBy: String? = null,
    ): Result<VacanciesPage>

    suspend fun getVacancyDetails(
        vacancyId: String,
    ): Result<VacancyDetails>
}
