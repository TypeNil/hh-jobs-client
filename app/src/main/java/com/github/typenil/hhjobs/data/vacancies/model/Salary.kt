package com.github.typenil.hhjobs.data.vacancies.model

/**
 * Public domain model representing salary information.
 */
data class Salary(
    val from: Int? = null,
    val to: Int? = null,
    val currency: String = "RUR",
    val gross: Boolean? = null,
) {
    val formatted: String
        get() {
            val symbol = currencySymbol(currency)
            val formattedFrom = from?.let { formatNumber(it) }
            val formattedTo = to?.let { formatNumber(it) }

            return when {
                formattedFrom != null && formattedTo != null -> "от $formattedFrom до $formattedTo $symbol"
                formattedFrom != null -> "от $formattedFrom $symbol"
                formattedTo != null -> "до $formattedTo $symbol"
                else -> "Зарплата не указана"
            }
        }

    private fun currencySymbol(curr: String): String {
        return when (curr.uppercase()) {
            "RUR", "RUB" -> "₽"
            "USD" -> "$"
            "EUR" -> "€"
            "KZT" -> "₸"
            "BYR", "BYN" -> "Br"
            "UAH" -> "₴"
            "GEL" -> "₾"
            "UZS" -> "so'm"
            else -> curr
        }
    }

    private fun formatNumber(number: Int): String {
        return "%,d".format(number).replace(',', ' ')
    }
}
