package com.guidovezzoni.gradle.respro.extensions

import com.guidovezzoni.gradle.respro.gradle.ResproExtension
import com.guidovezzoni.gradle.respro.model.ConfigModel

fun ResproExtension.toConfigModel(): ConfigModel {
    return ConfigModel(this.sourceFile)
}
