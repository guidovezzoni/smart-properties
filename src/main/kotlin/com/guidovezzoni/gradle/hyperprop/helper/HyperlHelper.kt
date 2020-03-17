package com.guidovezzoni.gradle.hyperprop.helper

import com.android.build.gradle.AppExtension
import com.guidovezzoni.gradle.hyperprop.extensions.*
import com.guidovezzoni.gradle.hyperprop.gradle.HyperpropExtension
import com.guidovezzoni.gradle.hyperprop.model.ConfigModel
import org.gradle.api.Project
import java.util.*

class HyperlHelper {
    private val entries = Properties()
    private lateinit var configExtension: HyperpropExtension
    private lateinit var extensionConfigModel: ConfigModel


    fun configure(project: Project) {
        configExtension = project.extensions.getByType(HyperpropExtension::class.java)
        extensionConfigModel = configExtension.toConfigModel()
//        System.out.printf("%s, %s!\n", extensionConfigModel.message, extensionConfigModel.recipient)

        if (!extensionConfigModel.sourceFile.exists()) {
            throw IllegalArgumentException("No valid file for parameter sourceFile - ${extensionConfigModel.sourceFile.absoluteFile} not found")
        }
        println("\n***** Loading config file ${extensionConfigModel.sourceFile.absoluteFile}")
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
        println("\n***** Hyper Properties found")
        properties.forEach { propKey, propValue ->
            val keyString = propKey.toString()
            val valueString = propValue.toString()

            val android = project.extensions.findByName("android") as AppExtension

            val finalValue = getEnvVar(keyString.cleanTokensUp()) ?: valueString
            val escapedValue = finalValue.doubleQuoted()

            println("* PropertyName=$keyString  *  finalValue=$finalValue")

            android.defaultConfig.buildConfigFieldStringIfRequested(keyString, escapedValue)
            android.defaultConfig.resValueStringIfRequired(keyString, escapedValue)

            if (keyString.isProjectExtProperty()) {
                project.extensions.extraProperties.set(keyString.cleanTokensUp(), finalValue)
            }

            if (keyString.isRootProjectExtProperty()) {
                project.rootProject.extensions.extraProperties.set(keyString.cleanTokensUp(), finalValue)
            }
        }
    }
}
