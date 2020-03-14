package com.guidovezzoni.gradle.hyperprop.extensions

const val BUILDCONFIG_TOKEN = ".BuildConfig"
const val RESOURCES_TOKEN = ".Resources"
const val DOUBLE_QUOTE = "\""

fun String.isBuildConfigProperty(): Boolean {
    return contains(BUILDCONFIG_TOKEN)
}

fun String.isResourcesProperty(): Boolean {
    return contains(RESOURCES_TOKEN)
}

fun String.doubleQuoted(): String {
    return "$DOUBLE_QUOTE$this$DOUBLE_QUOTE"
}

fun String.hasTokens():Boolean {
    return false
}

fun String.hasUnknownTokens():Boolean {
    return false
}

fun String.cleanTokensUp(): String {
    return this.replace(BUILDCONFIG_TOKEN, "")
        .replace(RESOURCES_TOKEN, "")
}

