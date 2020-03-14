package com.guidovezzoni.gradle.respro.extensions

import com.android.annotations.NonNull
import com.android.build.gradle.internal.dsl.BaseFlavor

const val BUILDCONFIG_STRING_TOKEN="String"
const val RESVALUE_STRING_TOKEN="string"

@Deprecated("To be removed after development")
const val PARAM_PREFIX=""

//todo capitalise the name
fun BaseFlavor.buildConfigFieldString(@NonNull name: String, @NonNull value: String) {
    this.buildConfigField(BUILDCONFIG_STRING_TOKEN, "$PARAM_PREFIX$name", value)
}

fun BaseFlavor.buildConfigFieldStringIfRequested(@NonNull propertyName: String, @NonNull value: String) {
    if (propertyName.isBuildConfigProperty()) {
        buildConfigFieldString(propertyName.cleanTokensUp(), value)
    }
}

fun BaseFlavor.resValueString(@NonNull name: String, @NonNull value: String) {
    this.resValue(RESVALUE_STRING_TOKEN, "$PARAM_PREFIX$name", value)
}

fun BaseFlavor.resValueStringIfRequired(@NonNull propertyName: String, @NonNull value: String) {
    if (propertyName.isResourcesProperty()) {
        resValueString(propertyName.cleanTokensUp(), value)
    }
}

