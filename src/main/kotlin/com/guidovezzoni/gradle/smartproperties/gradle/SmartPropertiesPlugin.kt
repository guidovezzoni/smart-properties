package com.guidovezzoni.gradle.smartproperties.gradle

import com.guidovezzoni.gradle.smartproperties.extensions.getAndroid
import com.guidovezzoni.gradle.smartproperties.extensions.getConfigurationForVariant
import com.guidovezzoni.gradle.smartproperties.helper.SmartProperties
import com.guidovezzoni.gradle.smartproperties.logger.CustomLogging
import com.guidovezzoni.gradle.smartproperties.model.ConfigScriptModel
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("UnstableApiUsage")
class SmartPropertiesPlugin : Plugin<Project> {
    private val logger = CustomLogging.getLogger(SmartPropertiesPlugin::class.java)

    override fun apply(project: Project) {
        val extension = project.extensions.create(
            SmartPropertiesExtension.EXTENSION_NAME,
            SmartPropertiesExtension::class.java,
            project
        )

        extension.productFlavors = project.container(ConfigScriptModel::class.java)

        project.getAndroid().applicationVariants.whenObjectAdded { androidVariant ->
            val variantInfo = extension.getConfigurationForVariant(androidVariant)

            logger.debug("AndroidVariant=${androidVariant.name}")
            logger.debug("SmartProperty VariantInfo=$variantInfo")

            val smartProperties = SmartProperties(variantInfo.ciEnvironmentPrefix)
            smartProperties.load(variantInfo.sourceFile)

            if (androidVariant.generateBuildConfigProvider.isPresent) {
                val generateBuildConfigTask =
                    project.tasks.create(
                        "generate${androidVariant.name.capitalize()}BuildConfigSmartProperties",
                        GenerateBuildConfigSmartPropertiesTask::class.java
                    ) {
                        it.entries = smartProperties
                        it.flavorName = androidVariant.flavorName
                    }
                androidVariant.generateBuildConfigProvider.get().dependsOn(generateBuildConfigTask)

                val generateResourcesTask = project.tasks.create(
                    "generate${androidVariant.name.capitalize()}ResourcesSmartProperties",
                    GenerateResourcesSmartProperties::class.java
                ) {
                    it.entries = smartProperties
                    it.flavorName = androidVariant.flavorName
                }
                // Not sure resources generation should depend on BuildConfig task, but it works correctly for now
                // TODO I need to identify the proper task
                androidVariant.generateBuildConfigProvider.get().dependsOn(generateResourcesTask)
            } else {
                throw Exception("Cannot find generateBuildConfigTask")
            }
        }
    }
}
