package com.guidovezzoni.gradle.smartproperties.extensions

const val BUILDCONFIG_TOKEN = ".BuildConfig"
const val RESOURCES_TOKEN = ".Resources"

const val DOUBLE_QUOTE = "\""
const val DOT = "."

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
