package com.guidovezzoni.gradle.smartproperties.extensions

import com.guidovezzoni.gradle.smartproperties.gradle.SmartPropertiesExtension
import com.guidovezzoni.gradle.smartproperties.model.ConfigModel

fun SmartPropertiesExtension.toConfigModel(): ConfigModel {
    return ConfigModel(
        this.sourceFile,
        this.ciEnvironmentPrefix
    )
}
