package com.github.typenil.hhjobs.data.vacancies.internal.remote

import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.VacanciesResponseDto
import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.VacancyDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit service definition for HeadHunter vacancies endpoints.
 * Internal to the data:vacancies domain.
 */
internal interface HeadHunterApiService {

    @GET("vacancies")
    suspend fun getVacancies(
        @Query("text") text: String? = null,
        @Query("area") area: String? = null,
        @Query("page") page: Int = 0,
        @Query("per_page") perPage: Int = 20,
        @Query("salary") salary: Int? = null,
        @Query("only_with_salary") onlyWithSalary: Boolean? = null,
        @Query("experience") experience: String? = null,
        @Query("employment") employment: String? = null,
        @Query("schedule") schedule: String? = null,
        @Query("order_by") orderBy: String? = null,
    ): VacanciesResponseDto

    @GET("vacancies/{vacancy_id}")
    suspend fun getVacancyDetails(
        @Path("vacancy_id") vacancyId: String,
    ): VacancyDetailsDto
}
