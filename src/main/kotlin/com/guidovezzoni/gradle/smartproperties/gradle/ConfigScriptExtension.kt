package com.guidovezzoni.gradle.smartproperties.gradle

import com.guidovezzoni.gradle.smartproperties.logger.CustomLogging
import groovy.lang.Closure
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

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
            throw IllegalArgumentException("Only one defaultConfig section allowed.")
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

    companion object {
        const val EXTENSION_NAME = "smartPropertiesPlugin"
    }
}
