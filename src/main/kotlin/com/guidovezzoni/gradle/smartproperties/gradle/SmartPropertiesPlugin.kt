package com.guidovezzoni.gradle.smartproperties.gradle

import com.android.build.gradle.AppExtension
import com.guidovezzoni.gradle.smartproperties.helper.SmartPropertieslHelper
import com.guidovezzoni.gradle.smartproperties.logger.CustomLogging
import org.gradle.api.Plugin
import org.gradle.api.Project

class SmartPropertiesPlugin : Plugin<Project> {
    private val smartlHelper = SmartPropertieslHelper()
    private val logger = CustomLogging.getLogger(SmartPropertiesPlugin::class.java)

    override fun apply(project: Project) {
        project.extensions.create(EXTENSION_NAME, SmartPropertiesExtension::class.java)

        checkAndroidVariant(project)

        project.afterEvaluate { proj -> smartlHelper.configure(proj) }
    }

    private fun checkAndroidVariant(project: Project) {
        val android = project.extensions.findByName("android") as AppExtension
        logger.debug("\n**** Available variants ****")
        android.applicationVariants.whenObjectAdded { appVariant ->
            logger.debug("* ${appVariant.name}")
        }
    }

    companion object {
        const val EXTENSION_NAME = "smartPropertiesPlugin"
    }
}
