package com.guidovezzoni.gradle.smartproperties.extensions

import com.android.annotations.NonNull
import com.android.build.gradle.internal.dsl.BaseFlavor

const val BUILDCONFIG_STRING_TOKEN = "String"
const val RESVALUE_STRING_TOKEN = "string"

//todo capitalise the name
fun BaseFlavor.buildConfigFieldString(@NonNull name: String, @NonNull value: String) {
    this.buildConfigField(BUILDCONFIG_STRING_TOKEN, name, value)
}

fun BaseFlavor.resValueString(@NonNull name: String, @NonNull value: String) {
    this.resValue(RESVALUE_STRING_TOKEN, name, value)
}
