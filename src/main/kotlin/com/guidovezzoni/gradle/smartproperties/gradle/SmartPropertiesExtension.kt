package com.guidovezzoni.gradle.smartproperties.gradle

import java.io.File

open class SmartPropertiesExtension(
    var sourceFile: File = File(DEFAULT_FILENAME),
    var ciEnvironmentPrefix: String = DEFAULT_CI_ENV_PREFIX
) {

    companion object {
        const val DEFAULT_FILENAME = "smart.properties"
        const val DEFAULT_CI_ENV_PREFIX = ""
    }
}
