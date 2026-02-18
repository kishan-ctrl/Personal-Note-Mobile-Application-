package com.example.personaltasknotes.util

/**
 * Secure coding practice #1:
 * Validate user input to prevent crashes, unexpected states, and abuse (e.g., very large inputs).
 * We keep max lengths to avoid memory/performance issues caused by huge text.
 */
object Validators {
    fun validateTitle(title: String): String? {
        if (title.trim().isEmpty()) return "Title cannot be empty"
        if (title.length > Constants.MAX_TITLE_LEN) return "Title too long (max ${Constants.MAX_TITLE_LEN})"
        return null
    }

    fun validateDescription(description: String): String? {
        if (description.length > Constants.MAX_DESC_LEN) return "Description too long (max ${Constants.MAX_DESC_LEN})"
        return null
    }
}