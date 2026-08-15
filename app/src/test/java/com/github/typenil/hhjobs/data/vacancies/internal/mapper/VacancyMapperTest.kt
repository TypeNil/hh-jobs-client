package com.github.typenil.hhjobs.data.vacancies.internal.mapper

import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.AreaDto
import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.ContactsDto
import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.EmployerDto
import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.ExperienceDto
import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.KeySkillDto
import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.LogoUrlsDto
import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.PhoneDto
import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.SalaryDto
import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.SnippetDto
import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.VacanciesResponseDto
import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.VacancyDetailsDto
import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.VacancyItemDto
import com.github.typenil.hhjobs.data.vacancies.model.Salary
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class VacancyMapperTest {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    @Test
    fun `salary formatting works correctly for different cases`() {
        val rangeSalary = Salary(from = 150000, to = 250000, currency = "RUR")
        assertEquals("от 150 000 до 250 000 ₽", rangeSalary.formatted)

        val fromSalary = Salary(from = 200000, to = null, currency = "RUB")
        assertEquals("от 200 000 ₽", fromSalary.formatted)

        val toSalary = Salary(from = null, to = 300000, currency = "USD")
        assertEquals("до 300 000 $", toSalary.formatted)

        val nullSalary = Salary(from = null, to = null)
        assertEquals("Зарплата не указана", nullSalary.formatted)
    }

    @Test
    fun `vacancy item dto maps correctly to domain vacancy`() {
        val dto = VacancyItemDto(
            id = "12345",
            name = "Android Developer",
            area = AreaDto(id = "1", name = "Москва"),
            salary = SalaryDto(from = 200000, to = 300000, currency = "RUR", gross = false),
            employer = EmployerDto(
                id = "999",
                name = "Yandex",
                logoUrls = LogoUrlsDto(size240 = "https://logo.com/240.png", size90 = "https://logo.com/90.png"),
                trusted = true,
            ),
            publishedAt = "2026-08-14T10:00:00+0300",
            snippet = SnippetDto(requirement = "Kotlin coroutines", responsibility = "Build apps"),
            experience = ExperienceDto(id = "between1And3", name = "От 1 года до 3 лет"),
            alternateUrl = "https://hh.ru/vacancy/12345",
        )

        val domain = dto.toDomain()

        assertEquals("12345", domain.id)
        assertEquals("Android Developer", domain.name)
        assertEquals("Москва", domain.area.name)
        assertNotNull(domain.salary)
        assertEquals("от 200 000 до 300 000 ₽", domain.salary?.formatted)
        assertEquals("Yandex", domain.employer.name)
        assertEquals("https://logo.com/240.png", domain.employer.logoUrl)
        assertTrue(domain.employer.isTrusted)
        assertEquals("Kotlin coroutines", domain.snippetRequirement)
        assertEquals("От 1 года до 3 лет", domain.experience)
        assertEquals("https://hh.ru/vacancy/12345", domain.alternateUrl)
    }

    @Test
    fun `vacancy details dto maps correctly to domain vacancy details`() {
        val dto = VacancyDetailsDto(
            id = "54321",
            name = "Senior Android Engineer",
            description = "<p>Full job description</p>",
            keySkills = listOf(KeySkillDto("Kotlin"), KeySkillDto("Jetpack Compose"), KeySkillDto("Hilt")),
            area = AreaDto(id = "2", name = "Санкт-Петербург"),
            employer = EmployerDto(id = "888", name = "Tinkoff"),
            publishedAt = "2026-08-14",
            contacts = ContactsDto(
                name = "HR Manager",
                email = "hr@tinkoff.ru",
                phones = listOf(PhoneDto(country = "7", city = "999", number = "123-45-67", comment = "с 10 до 18")),
            ),
        )

        val domain = dto.toDomain()

        assertEquals("54321", domain.id)
        assertEquals("Senior Android Engineer", domain.name)
        assertEquals("<p>Full job description</p>", domain.descriptionHtml)
        assertEquals(listOf("Kotlin", "Jetpack Compose", "Hilt"), domain.keySkills)
        assertEquals("Санкт-Петербург", domain.area.name)
        assertNull(domain.salary)
        assertEquals("HR Manager", domain.contacts?.name)
        assertEquals("hr@tinkoff.ru", domain.contacts?.email)
        assertEquals("+7 (999) 123-45-67 (с 10 до 18)", domain.contacts?.phones?.first())
    }

    @Test
    fun `deserializes full headhunter json response and maps to domain`() {
        val jsonPayload = """
            {
                "items": [
                    {
                        "id": "1001",
                        "name": "Lead Android Developer",
                        "area": { "id": "1", "name": "Москва" },
                        "salary": { "from": 400000, "to": 550000, "currency": "RUR", "gross": true },
                        "employer": { "id": "500", "name": "Ozon", "trusted": true },
                        "published_at": "2026-08-14T09:00:00+0300",
                        "snippet": { "requirement": "Architecture skills" }
                    }
                ],
                "found": 1,
                "pages": 1,
                "page": 0,
                "per_page": 20
            }
        """.trimIndent()

        val responseDto = json.decodeFromString<VacanciesResponseDto>(jsonPayload)
        val domainPage = responseDto.toDomain()

        assertEquals(1, domainPage.found)
        assertEquals(1, domainPage.pages)
        assertEquals(0, domainPage.page)
        assertEquals(20, domainPage.perPage)
        assertEquals(1, domainPage.items.size)

        val item = domainPage.items.first()
        assertEquals("1001", item.id)
        assertEquals("Lead Android Developer", item.name)
        assertEquals("Ozon", item.employer.name)
        assertEquals("от 400 000 до 550 000 ₽", item.salary?.formatted)
    }
}
