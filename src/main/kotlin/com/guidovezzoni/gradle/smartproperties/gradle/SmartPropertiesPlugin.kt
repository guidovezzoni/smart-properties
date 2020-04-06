package com.guidovezzoni.gradle.smartproperties.gradle

import com.android.build.gradle.AppExtension
import com.guidovezzoni.gradle.smartproperties.extensions.*
import com.guidovezzoni.gradle.smartproperties.logger.CustomLogging
import com.guidovezzoni.gradle.smartproperties.model.VariantInfo
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.util.*

@Suppress("unused", "UnstableApiUsage")
class SmartPropertiesPlugin : Plugin<Project> {
    private val logger = CustomLogging.getLogger(SmartPropertiesPlugin::class.java)

    override fun apply(project: Project) {
        val extension = project.extensions.create(
            SmartPropertiesExtension.EXTENSION_NAME,
            SmartPropertiesExtension::class.java,
            project
        )

        extension.productFlavors = project.container(ConfigScriptModel::class.java)

        val android = project.extensions.findByName("android") as AppExtension?
            ?: throw Exception("Not an Android application")

//        println("\n**** Available Android variants ****")
        android.applicationVariants.whenObjectAdded { androidVariant ->
//            val configExtension = project.extensions.getByType(SmartPropertiesExtension::class.java)

            val variantInfo = VariantInfo(variantName = androidVariant.name)

            extension.productFlavors?.forEach { productFlavor ->
                if (productFlavor.name.equals(androidVariant.flavorName))
                    variantInfo.sourceFile = productFlavor.sourceFile
            }
            if (variantInfo.sourceFile == null) {
                variantInfo.sourceFile = extension.getDefaultConfigSourceFile()
            }

            extension.productFlavors?.forEach { productFlavor ->
                if (productFlavor.name.equals(androidVariant.flavorName))
                    variantInfo.ciEnvironmentPrefix = productFlavor.ciEnvironmentPrefix
            }
            if (variantInfo.ciEnvironmentPrefix == null) {
                variantInfo.ciEnvironmentPrefix = extension.getDefaultConfigCiEnvironmentPrefix()
            }

            println("\n***** Plugin Extension **********")
            println("* VariantInfo=$variantInfo")


            if (variantInfo.sourceFile?.exists() != true) {
                throw IllegalArgumentException("No valid file for parameter sourceFile - ${variantInfo.sourceFile?.absoluteFile} not found")
            }
            logger.debug("\n***** Loading config file ${variantInfo.sourceFile?.absoluteFile}")
            val entries = Properties()
            entries.load(variantInfo.sourceFile?.reader())

            if (androidVariant.generateBuildConfigProvider.isPresent) {
                val task =
                    project.tasks.create("generateBuildConfigSmartProperties${androidVariant.name.capitalize()}") {

                        entries.forEach { propKey, propValue ->
                            val keyString = propKey.toString()
                            val valueString = propValue.toString()

//                        val finalValue = getEnvVar(keyString.cleanTokensUp()) ?: valueString
                            val escapedValue = valueString.doubleQuoted()

                            android.defaultConfig.buildConfigFieldStringIfRequested(keyString, escapedValue)
                        }
                    }
                androidVariant.generateBuildConfigProvider.get().dependsOn(task)
            }

            if (androidVariant.mergeResourcesProvider.isPresent) {
                val task = project.tasks.create("generateResourcesSmartProperties${androidVariant.name.capitalize()}") {

                    entries.forEach { propKey, propValue ->
                        val keyString = propKey.toString()
                        val valueString = propValue.toString()

//                        val finalValue = getEnvVar(keyString.cleanTokensUp()) ?: valueString
                        val escapedValue = valueString.doubleQuoted()

                        android.defaultConfig.resValueStringIfRequired(keyString, escapedValue)
                    }
                }
                androidVariant.mergeResourcesProvider.get().dependsOn(task)
            }
        }
    }

    private fun checkAndroidVariant(project: Project) {
        val flavour: NamedDomainObjectContainer<ConfigScriptModel> = project.container(
            ConfigScriptModel::class.java
        )
        project.extensions.add("productFlavors", flavour)

        logger.quiet("\n**** Available SmartProperties variants ****")
        flavour.forEach { appVariant ->
            logger.quiet("* ${appVariant.ciEnvironmentPrefix}")
        }
//
//        NamedDomainObjectContainer<ConfigModel> ribbonProductFlavors = project . container (EasyLauncherConfig)
//        project.extensions.add('productFlavors', ribbonProductFlavors)


    }
}
