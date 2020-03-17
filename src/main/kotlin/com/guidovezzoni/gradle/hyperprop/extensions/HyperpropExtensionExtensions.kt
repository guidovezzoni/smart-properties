package com.guidovezzoni.gradle.hyperprop.extensions

import com.guidovezzoni.gradle.hyperprop.gradle.HyperpropExtension
import com.guidovezzoni.gradle.hyperprop.model.ConfigModel

fun HyperpropExtension.toConfigModel(): ConfigModel {
    return ConfigModel(
        this.sourceFile,
        this.ciEnvironmentPrefix
    )
}
