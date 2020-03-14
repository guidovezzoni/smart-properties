package com.guidovezzoni.gradle.respro.gradle

import java.io.File

open class ResproExtension(
    var sourceFile: File = File(DEFAULT_FILENAME)
) {

    companion object {
        const val DEFAULT_FILENAME = "resources.properties"
    }
}
