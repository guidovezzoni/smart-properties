package com.guidovezzoni.gradle.smartproperties.extensions

const val BUILDCONFIG_TOKEN = ".BuildConfig"
const val RESOURCES_TOKEN = ".Resources"
const val PROJECT_EXT_TOKEN = ".ProjectExt"
const val ROOT_PROJ_EXT_TOKEN = ".RootProjExt"
const val DOUBLE_QUOTE = "\""

fun String.isBuildConfigProperty(): Boolean {
    return contains(BUILDCONFIG_TOKEN)
}

fun String.isResourcesProperty(): Boolean {
    return contains(RESOURCES_TOKEN)
}

fun String.isProjectExtProperty(): Boolean {
    return contains(PROJECT_EXT_TOKEN)
}

fun String.isRootProjectExtProperty(): Boolean {
    return contains(ROOT_PROJ_EXT_TOKEN)
}

fun String.doubleQuoted(): String {
    return "$DOUBLE_QUOTE$this$DOUBLE_QUOTE"
}

fun String.hasTokens(): Boolean {
    return false
}

fun String.hasUnknownTokens(): Boolean {
    return this.cleanTokensUp().hasTokens()
}

fun String.cleanTokensUp(): String {
    return this.replace(BUILDCONFIG_TOKEN, "")
        .replace(RESOURCES_TOKEN, "")
//        .replace(PROJECT_EXT_TOKEN, "")
//        .replace(ROOT_PROJ_EXT_TOKEN, "")
}
