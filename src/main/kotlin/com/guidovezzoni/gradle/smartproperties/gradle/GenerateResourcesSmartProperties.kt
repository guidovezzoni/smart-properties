package com.guidovezzoni.gradle.smartproperties.gradle

import com.android.build.gradle.AppExtension
import com.guidovezzoni.gradle.smartproperties.extensions.doubleQuoted
import com.guidovezzoni.gradle.smartproperties.extensions.resValueStringIfRequired
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import java.util.*

open class GenerateResourcesSmartProperties : DefaultTask() {
    @get:Internal
    internal lateinit var entries: Properties

    @TaskAction
    fun perform() {
        val android = project.extensions.findByName("android") as AppExtension?
            ?: throw Exception("Not an Android application")


        entries.forEach { propKey, propValue ->
            val keyString = propKey.toString()
            val valueString = propValue.toString()

//                        val finalValue = getEnvVar(keyString.cleanTokensUp()) ?: valueString
            val escapedValue = valueString.doubleQuoted()

            android.defaultConfig.resValueStringIfRequired(keyString, escapedValue)
        }
    }
}
