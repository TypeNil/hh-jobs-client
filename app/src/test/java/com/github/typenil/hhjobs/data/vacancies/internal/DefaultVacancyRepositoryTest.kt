package com.github.typenil.hhjobs.data.vacancies.internal

import com.github.typenil.hhjobs.data.vacancies.internal.remote.HeadHunterApiService
import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.AreaDto
import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.EmployerDto
import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.VacanciesResponseDto
import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.VacancyDetailsDto
import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.VacancyItemDto
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DefaultVacancyRepositoryTest {

    private val fakeApiService = object : HeadHunterApiService {
        override suspend fun getVacancies(
            text: String?,
            area: String?,
            page: Int,
            perPage: Int,
            salary: Int?,
            onlyWithSalary: Boolean?,
            experience: String?,
            employment: String?,
            schedule: String?,
            orderBy: String?,
        ): VacanciesResponseDto {
            return VacanciesResponseDto(
                items = listOf(
                    VacancyItemDto(
                        id = "1",
                        name = "Kotlin Developer",
                        area = AreaDto(id = "1", name = "Москва"),
                        employer = EmployerDto(name = "Tech Corp"),
                        publishedAt = "2026-08-14",
                    ),
                ),
                found = 1,
                pages = 1,
                page = 0,
                perPage = 20,
            )
        }

        override suspend fun getVacancyDetails(vacancyId: String): VacancyDetailsDto {
            return VacancyDetailsDto(
                id = vacancyId,
                name = "Kotlin Developer",
                description = "<p>Description</p>",
                area = AreaDto(id = "1", name = "Москва"),
                employer = EmployerDto(name = "Tech Corp"),
                publishedAt = "2026-08-14",
            )
        }
    }

    private val repository = DefaultVacancyRepository(fakeApiService)

    @Test
    fun `searchVacancies returns success with mapped domain page`() = runTest {
        val result = repository.searchVacancies(query = "Kotlin")

        assertTrue(result.isSuccess)
        val page = result.getOrNull()
        assertEquals(1, page?.found)
        assertEquals("Kotlin Developer", page?.items?.first()?.name)
    }

    @Test
    fun `getVacancyDetails returns success with mapped domain details`() = runTest {
        val result = repository.getVacancyDetails("123")

        assertTrue(result.isSuccess)
        val details = result.getOrNull()
        assertEquals("123", details?.id)
        assertEquals("<p>Description</p>", details?.descriptionHtml)
    }
}
