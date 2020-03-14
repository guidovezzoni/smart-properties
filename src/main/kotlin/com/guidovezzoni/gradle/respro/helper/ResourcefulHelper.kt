package com.guidovezzoni.gradle.respro.helper

import com.android.build.gradle.AppExtension
import com.guidovezzoni.gradle.respro.extensions.*
import com.guidovezzoni.gradle.respro.gradle.ResproExtension
import org.gradle.api.Project
import java.util.*

class ResourcefulHelper {
    private val entries = Properties()
    private lateinit var configExtension: ResproExtension


    fun configure(project: Project) {
        configExtension = project.extensions.getByType(ResproExtension::class.java)
        val extensionConfigModel = configExtension.toConfigModel()
//        System.out.printf("%s, %s!\n", extensionConfigModel.message, extensionConfigModel.recipient)

        if (!extensionConfigModel.sourceFile.exists()) {
            throw IllegalArgumentException("No valid file for parameter sourceFile - ${extensionConfigModel.sourceFile.absoluteFile} not found")
        }
        println("\n***** Loading config file ${extensionConfigModel.sourceFile.absoluteFile}")
        entries.load(extensionConfigModel.sourceFile.reader())

        addResources(project, entries)
    }


    private fun addResources(project: Project, properties: Properties) {
        println("\n***** Resourceful Property found")
        properties.forEach() { key, value ->
            val android = project.extensions.findByName("android") as AppExtension

            val cleansedPropertyName: String = key.toString().cleanTokensUp()

            val finalValue: String = value.toString() //getBambooOrEnvOrDefault(cleansedPropertyName, propertyValue)
            val escapedValue = finalValue.doubleQuoted()

            println("* cleansedPropertyName=$cleansedPropertyName finalValue=$finalValue")

//            if (key.toString().isBuildConfigProperty()) {
//                android.defaultConfig.buildConfigField(cleansedPropertyName, escapedValue)
//            }
            android.defaultConfig.buildConfigFieldStringIfRequested(key.toString(), escapedValue)


//            if (key.toString().isResourcesProperty()) {
//                android.defaultConfig.resValue("string", "BINARY_$cleansedPropertyName", escapedValue)
//            }
            android.defaultConfig.resValueStringIfRequired(key.toString(), escapedValue)
        }
    }


}