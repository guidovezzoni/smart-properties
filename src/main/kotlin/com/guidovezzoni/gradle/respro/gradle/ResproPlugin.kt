package com.guidovezzoni.gradle.respro.gradle

import com.android.build.gradle.AppExtension
import com.guidovezzoni.gradle.respro.helper.ResourcefulHelper
import org.gradle.api.Plugin
import org.gradle.api.Project

class ResproPlugin : Plugin<Project> {
    private val resourcefulHelper = ResourcefulHelper()

    override fun apply(project: Project) {
        project.extensions.create(EXTENSION_NAME, ResproExtension::class.java)

        checkAndroidVariant(project)

        project.afterEvaluate { proj -> resourcefulHelper.configure(proj) }
    }

    private fun checkAndroidVariant(project: Project) {
        val android = project.extensions.findByName("android") as AppExtension
        println("\n**** Available variants ****")
        android.applicationVariants.whenObjectAdded { appVariant ->
            println("* ${appVariant.name}")
        }
    }

    companion object {
        const val EXTENSION_NAME = "resourcefulPropertiesPlugin"
//        const val TASK_NAME = "hello"
    }
}
