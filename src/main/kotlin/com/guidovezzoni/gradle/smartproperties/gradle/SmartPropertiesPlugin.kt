package com.guidovezzoni.gradle.smartproperties.gradle

import com.guidovezzoni.gradle.smartproperties.extensions.getAndroid
import com.guidovezzoni.gradle.smartproperties.extensions.getConfigurationForVariant
import com.guidovezzoni.gradle.smartproperties.logger.CustomLogging
import com.guidovezzoni.gradle.smartproperties.properties.SmartProperties
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Main plugin class
 */
@Suppress("UnstableApiUsage")
class SmartPropertiesPlugin : Plugin<Project> {
    private val logger = CustomLogging.getLogger(SmartPropertiesPlugin::class.java)

    override fun apply(project: Project) {
        val extension = project.extensions.create(
            ConfigScriptExtension.EXTENSION_NAME,
            ConfigScriptExtension::class.java,
            project
        )

        extension.productFlavors = project.container(ConfigScriptBlock::class.java)

        project.getAndroid().applicationVariants.whenObjectAdded { androidVariant ->
            val variantInfo = extension.getConfigurationForVariant(androidVariant)

            logger.debug("AndroidVariant=${androidVariant.name}")
            logger.debug("SmartProperty VariantInfo=$variantInfo")

            val smartProperties = SmartProperties(variantInfo.ciEnvironmentPrefix)
            smartProperties.load(variantInfo.sourceFile)

            if (androidVariant.generateBuildConfigProvider.isPresent) {
                val taskVariantName = androidVariant.name.capitalize()

                val generateBuildConfigTask =
                    project.tasks.create(
                        "generate${taskVariantName}BuildConfigSmartProperties",
                        GenerateBuildConfigSmartPropertiesTask::class.java
                    ) { task ->
                        task.entries = smartProperties
                        task.flavorName = androidVariant.flavorName
                    }
                androidVariant.generateBuildConfigProvider.get().dependsOn(generateBuildConfigTask)

                val generateResourcesTask = project.tasks.create(
                    "generate${taskVariantName}ResourcesSmartProperties",
                    GenerateResourcesSmartPropertiesTask::class.java
                ) { task ->
                    task.entries = smartProperties
                    task.flavorName = androidVariant.flavorName
                }
                // Not sure resources generation should depend on BuildConfig task, but it works correctly for now
                // TODO I need to identify the proper task
                androidVariant.generateBuildConfigProvider.get().dependsOn(generateResourcesTask)
            } else {
                throw IllegalArgumentException("Cannot find generateBuildConfigTask")
            }
        }
    }
}
