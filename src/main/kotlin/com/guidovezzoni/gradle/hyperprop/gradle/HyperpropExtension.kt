package com.guidovezzoni.gradle.hyperprop.gradle

import java.io.File

open class HyperpropExtension(
    var sourceFile: File = File(DEFAULT_FILENAME),
    var ciEnvironmentPrefix: String = DEFAULT_CI_ENV_PREFIX
) {

    companion object {
        const val DEFAULT_FILENAME = "hyper.properties"
        const val DEFAULT_CI_ENV_PREFIX = ""
    }
}
