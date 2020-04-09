package com.guidovezzoni.gradle.smartproperties.helper

import com.android.build.gradle.AppExtension
import com.guidovezzoni.gradle.smartproperties.extensions.*
import com.guidovezzoni.gradle.smartproperties.gradle.SmartPropertiesExtension
import com.guidovezzoni.gradle.smartproperties.logger.CustomLogging
import com.guidovezzoni.gradle.smartproperties.model.ConfigModel
import org.gradle.api.Project
import java.util.*

class SmartPropertieslHelper {
    private val logger = CustomLogging.getLogger(SmartPropertieslHelper::class.java)
    private val entries = Properties()
    private lateinit var configExtension: SmartPropertiesExtension
    private lateinit var extensionConfigModel: ConfigModel


    fun configure(project: Project) {
        configExtension = project.extensions.getByType(SmartPropertiesExtension::class.java)
        extensionConfigModel = configExtension.toConfigModel()

        if (!extensionConfigModel.sourceFile.exists()) {
            throw IllegalArgumentException("No valid file for parameter sourceFile - ${extensionConfigModel.sourceFile.absoluteFile} not found")
        }
        logger.debug("\n***** Loading config file ${extensionConfigModel.sourceFile.absoluteFile}")
        entries.load(extensionConfigModel.sourceFile.reader())

        addResources(project, entries)
    }

    /**
     * Improvement: make the priority configurable
     */
    private fun getEnvVar(propName: String): String? {
        val ciEnvVar = System.getenv(extensionConfigModel.ciEnvironmentPrefix + propName)
        ciEnvVar?.let { return ciEnvVar }

        val envVar = System.getenv(propName)
        envVar?.let { return envVar }

        return null
    }

    private fun addResources(project: Project, properties: Properties) {
        logger.debug("\n***** Smart Properties found")
        properties.forEach { propKey, propValue ->
            val keyString = propKey.toString()
            val valueString = propValue.toString()

            val android = project.extensions.findByName("android") as AppExtension

            val finalValue = getEnvVar(keyString.cleanUpTokens()) ?: valueString
            val escapedValue = finalValue.doubleQuoted()

            logger.debug("* PropertyName=$keyString  *  finalValue=$finalValue")

            android.defaultConfig.buildConfigFieldStringIfRequested(keyString, escapedValue)
            android.defaultConfig.resValueStringIfRequired(keyString, escapedValue)

            if (keyString.isProjectExtProperty()) {
                project.extensions.extraProperties.set(keyString.cleanUpTokens(), finalValue)
            }

            if (keyString.isRootProjectExtProperty()) {
                project.rootProject.extensions.extraProperties.set(keyString.cleanUpTokens(), finalValue)
            }
        }
    }
}
