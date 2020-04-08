package com.guidovezzoni.gradle.smartproperties.model

import java.io.File

open class ConfigScriptModel {

    constructor()

    @Suppress("unused")
    constructor(name: String) {
        this.name = name
    }

    var name: String? = null
    var sourceFile: File? = null
    var ciEnvironmentPrefix: String? = null
}
