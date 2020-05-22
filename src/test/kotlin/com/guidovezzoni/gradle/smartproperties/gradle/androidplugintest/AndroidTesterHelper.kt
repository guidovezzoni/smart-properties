package com.guidovezzoni.gradle.smartproperties.gradle.androidplugintest

import org.gradle.internal.impldep.org.junit.rules.TemporaryFolder
import java.io.File

class AndroidTesterHelper(
    private val temporaryFolder: TemporaryFolder,
    private val injectedClassPath: String = "",
    private val kotlinVersion: String = "1.3.72",
    private val gradlePluginVersion: String = "3.6.3"
) {

    fun writeAndroidProject(type: Type) {
        temporaryFolder.create()

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

    private fun File.writeFromRes(type: Type, name: String) {
        val resPath: String = when (type) {
            Type.GROOVY_BUILDSCRIPT_ANDROID -> "groovy-buildscript/"
            Type.GROOVY_PLUGINS_ANDROID -> "groovy-plugins/"
        } + name

        val resource =
            AndroidTesterHelper::class.java.classLoader.getResource(resPath)
                ?: AndroidTesterHelper::class.java.classLoader.getResource("common/$name")

        this.writeText(
            resource.readText()
                .replace("{kotlinVersion}", kotlinVersion)
                .replace("{classPath}", getBuildscriptClassPath)
                .replace("{gradlePluginVersion}", gradlePluginVersion)
        )
    }

    private fun TemporaryFolder.writeFromRes(type: Type, name: String) {
        this.newFile(name)
            .writeFromRes(type, name)
    }
}
