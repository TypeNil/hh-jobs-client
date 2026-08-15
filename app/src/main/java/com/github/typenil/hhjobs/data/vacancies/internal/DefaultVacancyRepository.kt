package com.github.typenil.hhjobs.data.vacancies.internal

import com.github.typenil.hhjobs.data.vacancies.VacancyRepository
import com.github.typenil.hhjobs.data.vacancies.internal.mapper.toDomain
import com.github.typenil.hhjobs.data.vacancies.internal.remote.HeadHunterApiService
import com.github.typenil.hhjobs.data.vacancies.model.VacanciesPage
import com.github.typenil.hhjobs.data.vacancies.model.VacancyDetails
import javax.inject.Inject

internal class DefaultVacancyRepository @Inject constructor(
    private val apiService: HeadHunterApiService,
) : VacancyRepository {

    override suspend fun searchVacancies(
        query: String?,
        area: String?,
        page: Int,
        perPage: Int,
        salary: Int?,
        onlyWithSalary: Boolean?,
        experience: String?,
        employment: String?,
        schedule: String?,
        orderBy: String?,
    ): Result<VacanciesPage> = runCatching {
        apiService.getVacancies(
            text = query,
            area = area,
            page = page,
            perPage = perPage,
            salary = salary,
            onlyWithSalary = onlyWithSalary,
            experience = experience,
            employment = employment,
            schedule = schedule,
            orderBy = orderBy,
        ).toDomain()
    }

    override suspend fun getVacancyDetails(
        vacancyId: String,
    ): Result<VacancyDetails> = runCatching {
        apiService.getVacancyDetails(vacancyId).toDomain()
    }
}
