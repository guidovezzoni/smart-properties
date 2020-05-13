package com.guidovezzoni.gradle.smartproperties.extensions

const val BUILDCONFIG_TOKEN = ".BuildConfig"
const val RESOURCES_TOKEN = ".Resources"

const val DOUBLE_QUOTE = "\""
const val DOT = "."
const val REPLACEMENT_PATTERN = "$1_$2"
const val REGEX_PATTERN_LOWERCASE_TO_UPPERCASE = "([a-z])([A-Z])"
const val REGEX_PATTERN_LETTER_TO_NUMBER = "([A-Za-z])([0-9])"
const val REGEX_PATTERN_NUMBER_TO_LETTER = "([0-9])([A-Za-z])"

fun String.isBuildConfigProperty(): Boolean {
    return contains(BUILDCONFIG_TOKEN)
}

fun String.isResourcesProperty(): Boolean {
    return contains(RESOURCES_TOKEN)
}

fun String.doubleQuoted(): String {
    return "$DOUBLE_QUOTE$this$DOUBLE_QUOTE"
}

fun String.hasKnownTokens(): Boolean {
    return isBuildConfigProperty() || isResourcesProperty()
}

fun String.hasUnknownTokens(): Boolean {
    return this.cleanUpTokens().contains(DOT)
}

fun String.cleanUpTokens(): String {
    return this.replace(BUILDCONFIG_TOKEN, "")
        .replace(RESOURCES_TOKEN, "")
}

/**
 * Introduces an underscore when:
 * * a lowercase letter is followed by a uppercase letter
 * * a letter is followed by a number
 * * a number is followed by a letter
 */
fun String.addUnderscoreBetweenWords(): String {
    return this
        .replace(REGEX_PATTERN_LOWERCASE_TO_UPPERCASE.toRegex(), REPLACEMENT_PATTERN)
        .replace(REGEX_PATTERN_LETTER_TO_NUMBER.toRegex(), REPLACEMENT_PATTERN)
        .replace(REGEX_PATTERN_NUMBER_TO_LETTER.toRegex(), REPLACEMENT_PATTERN)
}

fun String.toXmlNamingConvention(): String {
    return this.addUnderscoreBetweenWords()
        .toLowerCase()
}

fun String.toConstantNamingConvention(): String {
    return this
        .addUnderscoreBetweenWords()
        .toUpperCase()
}
