package com.guidovezzoni.gradle.hyperprop.gradle

import com.android.build.gradle.AppExtension
import com.guidovezzoni.gradle.hyperprop.helper.HyperlHelper
import com.guidovezzoni.gradle.hyperprop.logger.CustomLogging
import org.gradle.api.Plugin
import org.gradle.api.Project

class HyperpropPlugin : Plugin<Project> {
    private val hyperlHelper = HyperlHelper()
    private val logger = CustomLogging.getLogger(HyperpropPlugin::class.java)

    override fun apply(project: Project) {
        project.extensions.create(EXTENSION_NAME, HyperpropExtension::class.java)

        checkAndroidVariant(project)

        project.afterEvaluate { proj -> hyperlHelper.configure(proj) }
    }

    private fun checkAndroidVariant(project: Project) {
        val android = project.extensions.findByName("android") as AppExtension
        logger.debug("\n**** Available variants ****")
        android.applicationVariants.whenObjectAdded { appVariant ->
            logger.debug("* ${appVariant.name}")
        }
    }

    companion object {
        const val EXTENSION_NAME = "hyperPropertiesPlugin"
    }
}
