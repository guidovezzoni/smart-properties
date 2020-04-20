package com.guidovezzoni.gradle.smartproperties.gradle

import com.guidovezzoni.gradle.smartproperties.logger.CustomLogging
import groovy.lang.Closure
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import java.io.File

/**
 * Extension class for plugin configuration
 */
open class ConfigScriptExtension(private val project: Project) {
    val logger = CustomLogging.getLogger(ConfigScriptExtension::class.java)

    var defaultConfig: ConfigScriptBlock? = null
    var productFlavors: NamedDomainObjectContainer<ConfigScriptBlock>? = null

    @Suppress("unused")
    fun defaultConfig(defaultDef: Closure<*>): ConfigScriptBlock? {
        if (defaultConfig != null) {
            throw IllegalArgumentException("Only one defaultConfig closure allowed")
        }
        defaultConfig = ConfigScriptBlock()

        project.configure(defaultConfig!!, defaultDef)
        defaultDef.delegate = defaultConfig

        return defaultConfig
    }

    @Suppress("unused")
    fun productFlavors(action: Action<in NamedDomainObjectContainer<ConfigScriptBlock>?>) {
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
