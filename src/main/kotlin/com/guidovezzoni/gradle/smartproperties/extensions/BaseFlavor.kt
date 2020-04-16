package com.guidovezzoni.gradle.smartproperties.extensions

import com.android.build.gradle.internal.dsl.BaseFlavor

const val BUILDCONFIG_STRING_TOKEN = "String"
const val RESVALUE_STRING_TOKEN = "string"

//todo capitalise the name
fun BaseFlavor.buildConfigFieldString(name: String, value: String, ignoreBuildConfigSyntax: Boolean) {
    this.buildConfigField(
        BUILDCONFIG_STRING_TOKEN,
        name,
        if (ignoreBuildConfigSyntax) value else value.toConstantSyntax()
    )
}

fun BaseFlavor.resValueString(name: String, value: String) {
    this.resValue(RESVALUE_STRING_TOKEN, name, value)
}
