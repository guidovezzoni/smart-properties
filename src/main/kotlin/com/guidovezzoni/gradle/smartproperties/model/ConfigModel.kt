package com.guidovezzoni.gradle.smartproperties.model

import java.io.File

data class ConfigModel(
    var sourceFile: File,
    var ciEnvironmentPrefix: String
)
