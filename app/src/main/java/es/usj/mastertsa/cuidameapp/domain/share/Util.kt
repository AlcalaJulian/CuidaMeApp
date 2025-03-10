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
    }
}