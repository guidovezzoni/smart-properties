package com.guidovezzoni.gradle.smartproperties.gradle.androidplugintest

import org.gradle.internal.impldep.org.junit.rules.TemporaryFolder
import java.io.File

class AndroidTesterHelper(
    private val temporaryFolder: TemporaryFolder,
    private val injectedClassPath: String = "",
    private val pluginUnderTest: String = "",
    private var pluginAndroidModuleBuildGradleSection: String = "",
    private val kotlinVersion: String = "1.3.72",
    private val gradlePluginVersion: String = "3.6.3"
) {

    fun writeAndroidProject(
        type: Type,
        customPluginAndroidModuleBuildGradleSection: String = ""
    ) {
        temporaryFolder.create()

        pluginAndroidModuleBuildGradleSection = customPluginAndroidModuleBuildGradleSection

        temporaryFolder.writeFromRes(type, "settings.gradle")
        temporaryFolder.writeFromRes(type, "build.gradle")
        temporaryFolder.writeFromRes(type, "gradle.properties")

        temporaryFolder.newFolder("app")
        temporaryFolder.writeFromRes(type, "app/build.gradle")

        temporaryFolder.newFolder("app", "src", "main")
        temporaryFolder.writeFromRes(Type.GROOVY_BUILDSCRIPT_ANDROID, "app/src/main/AndroidManifest.xml")
    }

    private val getBuildscriptClassPath: String
        get() = if (injectedClassPath.isBlank()) "" else "classpath files($injectedClassPath)"

    private val getApplyPluginUnderTest: String
        get() = if (pluginUnderTest.isBlank()) "" else "apply plugin: '$pluginUnderTest'"

    private fun getIdPluginUnderTest(apply: Boolean = true): String {
        val applyDependencySpec = if (apply) "" else " apply false"
        return if (pluginUnderTest.isBlank())
            ""
        else
            "id \"$pluginUnderTest\"$applyDependencySpec"
    }

    private fun File.writeFromRes(type: Type, name: String) {
        val resPath: String = when (type) {
            Type.GROOVY_BUILDSCRIPT_ANDROID -> "groovy-buildscript/"
            Type.GROOVY_PLUGINS_ANDROID -> "groovy-plugins/"
            Type.GROOVY_SIMPLE -> "groovy-simple/"
        } + name

        val resource =
            AndroidTesterHelper::class.java.classLoader.getResource(resPath)
                ?: AndroidTesterHelper::class.java.classLoader.getResource("common/$name")

        this.writeText(
            resource.readText()
                .replace("{classPath}", getBuildscriptClassPath)
                .replace("{applyPluginUnderTest}", getApplyPluginUnderTest)
                .replace("{idPluginUnderTestApplyFalse}", getIdPluginUnderTest(false))
                .replace("{idPluginUnderTest}", getIdPluginUnderTest())
                .replace("{kotlinVersion}", kotlinVersion)
                .replace("{gradlePluginVersion}", gradlePluginVersion)
                .replace("{appBuildGradleSection}", pluginAndroidModuleBuildGradleSection)
        )
    }

    private fun TemporaryFolder.writeFromRes(type: Type, name: String) {
        this.newFile(name)
            .writeFromRes(type, name)
    }
}
