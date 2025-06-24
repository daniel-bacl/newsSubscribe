package com.newssubscribe.util

object InputValidator {

    private val blacklistPatterns = listOf(
        Regex("--"),
        Regex("#"),
        Regex("/\\*.*?\\*/"),
        Regex("(?i)or\\s+1=1"),
        Regex("(?i)union\\s+select"),
        Regex("(?i)(drop|insert|delete|update|select)\\s"),
        Regex("[\"';]"),
        Regex("(?i)<\\s*script"),
        Regex("(?i)<\\s*img"),
        Regex("(?i)onerror\\s*="),
        Regex("(?i)javascript:")
    )

    fun isMaliciousInput(input: String): Boolean {
        return blacklistPatterns.any { it.containsMatchIn(input) }
    }
}
