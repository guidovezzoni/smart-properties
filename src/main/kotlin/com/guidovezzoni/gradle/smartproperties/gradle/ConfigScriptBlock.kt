package com.guidovezzoni.gradle.smartproperties.gradle

import java.io.File

/**
 * Config script block for plugin configuration
 */
open class ConfigScriptBlock {

    constructor()

    @Suppress("unused")
    constructor(name: String) {
        this.name = name
    }

    var name: String? = null
    var sourceFile: File? = null
    var ciEnvironmentPrefix: String? = null
    var ignoreBuildConfigSyntax :Boolean? = null
}
