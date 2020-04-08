package com.guidovezzoni.gradle.smartproperties.gradle

import com.guidovezzoni.gradle.smartproperties.logger.CustomLogging
import com.guidovezzoni.gradle.smartproperties.model.ConfigScriptModel
import groovy.lang.Closure
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import java.io.File

open class SmartPropertiesExtension(private val project: Project) {
    val logger = CustomLogging.getLogger(SmartPropertiesExtension::class.java)

    var defaultConfig: ConfigScriptModel? = null
    var productFlavors: NamedDomainObjectContainer<ConfigScriptModel>? = null

    @Suppress("unused")
    fun defaultConfig(defaultDef: Closure<*>): ConfigScriptModel? {
        if (defaultConfig != null) {
            throw Exception("Only one defaultConfig closure allowed")
        }
        defaultConfig = ConfigScriptModel()

        project.configure(defaultConfig!!, defaultDef)
        defaultDef.delegate = defaultConfig

        return defaultConfig
    }

    @Suppress("unused")
    fun productFlavors(action: Action<in NamedDomainObjectContainer<ConfigScriptModel>?>) {
        action.execute(productFlavors)
    }

    fun getDefaultConfigSourceFile(): File {
        return defaultConfig?.sourceFile ?: File(DEFAULT_FILENAME)
    }

    fun getDefaultConfigCiEnvironmentPrefix(): String {
        return defaultConfig?.ciEnvironmentPrefix ?: DEFAULT_CI_ENV_PREFIX
    }

    companion object {
        const val DEFAULT_FILENAME = "smart.properties"
        const val DEFAULT_CI_ENV_PREFIX = ""
        const val EXTENSION_NAME = "smartPropertiesPlugin"
    }
}
