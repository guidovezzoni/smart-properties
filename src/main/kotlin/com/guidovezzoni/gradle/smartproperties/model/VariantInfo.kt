package com.guidovezzoni.gradle.smartproperties.model

import java.io.File

data class VariantInfo(
    val androidVariantName: String,
    val productFlavorName: String,
    val sourceFile: File,
    val ciEnvironmentPrefix: String
)