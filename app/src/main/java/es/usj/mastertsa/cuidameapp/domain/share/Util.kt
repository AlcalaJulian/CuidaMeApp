package es.usj.mastertsa.cuidameapp.domain.share

import android.util.Patterns

class Util {
    companion object {
        fun validateEmail(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun validateName(text: String): Boolean {
            val namePattern = "^[A-Za-zÀ-ÿ' -]+\$".toRegex()
            return text.matches(namePattern)
        }

        fun calculateDays(quantity: Int, recurrence: String): String {
            // Normalize the recurrence string by trimming spaces and converting to lowercase
            val normalizedRecurrence = recurrence.split(" ")[1].trim().lowercase()

            return when (// Handle "day" and "days" cases
                normalizedRecurrence) {
                "día" -> if (quantity == 1) normalizedRecurrence else "dias"

                // Handle "week" and "weeks" cases
                "semana" -> if (quantity == 1) normalizedRecurrence else normalizedRecurrence+"s"

                // If recurrence type is invalid, throw an exception
                else -> throw IllegalArgumentException("Invalid recurrence type. Only 'day', 'days', 'week', or 'weeks' are allowed.")
            }
        }
    }
}