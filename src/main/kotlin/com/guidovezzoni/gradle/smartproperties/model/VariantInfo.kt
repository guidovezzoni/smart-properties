package com.guidovezzoni.gradle.smartproperties.model

import java.io.File

data class VariantInfo(
    var variantName: String? = null,
    var sourceFile: File? = null,
    var ciEnvironmentPrefix: String? = null
)