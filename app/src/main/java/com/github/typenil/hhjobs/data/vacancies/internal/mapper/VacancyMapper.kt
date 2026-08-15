package com.github.typenil.hhjobs.data.vacancies.internal.mapper

import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.AreaDto
import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.ContactsDto
import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.EmployerDto
import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.PhoneDto
import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.SalaryDto
import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.VacanciesResponseDto
import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.VacancyDetailsDto
import com.github.typenil.hhjobs.data.vacancies.internal.remote.dto.VacancyItemDto
import com.github.typenil.hhjobs.data.vacancies.model.Area
import com.github.typenil.hhjobs.data.vacancies.model.Contacts
import com.github.typenil.hhjobs.data.vacancies.model.Employer
import com.github.typenil.hhjobs.data.vacancies.model.Salary
import com.github.typenil.hhjobs.data.vacancies.model.VacanciesPage
import com.github.typenil.hhjobs.data.vacancies.model.Vacancy
import com.github.typenil.hhjobs.data.vacancies.model.VacancyDetails

internal fun AreaDto.toDomain(): Area = Area(
    id = id,
    name = name,
)

internal fun SalaryDto.toDomain(): Salary = Salary(
    from = from,
    to = to,
    currency = currency ?: "RUR",
    gross = gross,
)

internal fun EmployerDto.toDomain(): Employer = Employer(
    id = id,
    name = name,
    logoUrl = logoUrls?.size240 ?: logoUrls?.size90 ?: logoUrls?.original,
    alternateUrl = alternateUrl,
    isTrusted = trusted,
)

internal fun PhoneDto.toFormattedString(): String {
    val prefix = if (!country.isNullOrBlank() && !city.isNullOrBlank()) {
        "+$country ($city) "
    } else {
        ""
    }
    val fullNumber = prefix + (number ?: "")
    val commentSuffix = if (!comment.isNullOrBlank()) " ($comment)" else ""
    return fullNumber + commentSuffix
}

internal fun ContactsDto.toDomain(): Contacts = Contacts(
    name = name,
    email = email,
    phones = phones.map { it.toFormattedString() },
)

internal fun VacancyItemDto.toDomain(): Vacancy = Vacancy(
    id = id,
    name = name,
    employer = employer.toDomain(),
    salary = salary?.toDomain(),
    area = area.toDomain(),
    experience = experience?.name,
    schedule = schedule?.name,
    employment = employment?.name,
    snippetRequirement = snippet?.requirement,
    snippetResponsibility = snippet?.responsibility,
    publishedAt = publishedAt,
    alternateUrl = alternateUrl,
)

internal fun VacancyDetailsDto.toDomain(): VacancyDetails = VacancyDetails(
    id = id,
    name = name,
    descriptionHtml = description,
    keySkills = keySkills.map { it.name },
    employer = employer.toDomain(),
    salary = salary?.toDomain(),
    area = area.toDomain(),
    experience = experience?.name,
    schedule = schedule?.name,
    employment = employment?.name,
    publishedAt = publishedAt,
    alternateUrl = alternateUrl,
    contacts = contacts?.toDomain(),
)

internal fun VacanciesResponseDto.toDomain(): VacanciesPage = VacanciesPage(
    items = items.map { it.toDomain() },
    found = found,
    pages = pages,
    page = page,
    perPage = perPage,
)
