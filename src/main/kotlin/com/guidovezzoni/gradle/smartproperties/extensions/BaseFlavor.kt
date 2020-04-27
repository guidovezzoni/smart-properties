package com.guidovezzoni.gradle.smartproperties.extensions

import com.android.build.gradle.internal.dsl.BaseFlavor

const val BUILDCONFIG_STRING_TOKEN = "String"
const val RESVALUE_STRING_TOKEN = "string"

fun BaseFlavor.buildConfigFieldString(name: String, value: String, keepPropertySyntax: Boolean) {
    this.buildConfigField(
        BUILDCONFIG_STRING_TOKEN,
        if (keepPropertySyntax) name else name.toConstantSyntax(),
        value
    )
}

fun BaseFlavor.resValueString(name: String, value: String, keepPropertySyntax: Boolean) {
    this.resValue(
        RESVALUE_STRING_TOKEN,
        if (keepPropertySyntax) name else name.toXmlSyntax(),
        value
    )
}
