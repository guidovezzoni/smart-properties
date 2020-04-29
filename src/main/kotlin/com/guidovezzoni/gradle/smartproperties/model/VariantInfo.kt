package com.guidovezzoni.gradle.smartproperties.model

import java.io.File

/**
 * All the configuration for a specific variant - as obtained from the config script
 */
data class VariantInfo(
    val androidVariantName: String,
    val productFlavorName: String,
    val sourceFile: File,
    val ciEnvironmentPrefix: String,
    val dontRenameProperty: Boolean
)
