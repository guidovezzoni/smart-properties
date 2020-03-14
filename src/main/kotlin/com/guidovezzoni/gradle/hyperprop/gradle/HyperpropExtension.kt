package com.guidovezzoni.gradle.hyperprop.gradle

import java.io.File

open class HyperpropExtension(
    var sourceFile: File = File(DEFAULT_FILENAME)
) {

    companion object {
        const val DEFAULT_FILENAME = "hyper.properties"
    }
}
