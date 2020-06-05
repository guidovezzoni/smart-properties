package com.guidovezzoni.gradle.smartproperties.gradle

import com.guidovezzoni.gradle.smartproperties.gradle.androidplugintest.AndroidTesterHelper
import com.guidovezzoni.gradle.smartproperties.gradle.androidplugintest.Type
import org.gradle.internal.impldep.org.junit.Rule
import org.gradle.internal.impldep.org.junit.rules.TemporaryFolder
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.io.File


internal class SmartPropertiesPluginFunctionalTest {
    @Rule
    @JvmField
    val testProjectDir: TemporaryFolder = TemporaryFolder()

    lateinit var androidTesterHelper: AndroidTesterHelper

    lateinit var settingsFile: File
    lateinit var rootGradleFile: File

    @BeforeEach
    internal fun setUp() {
        val pluginClassPath: MutableList<String> = mutableListOf()
        File("build/createClasspathManifest/plugin-classpath.txt")
            .forEachLine { pluginClassPath.add(it) }

        val classpathString = pluginClassPath.joinToString(separator = "', '", prefix = "'", postfix = "'")

        androidTesterHelper = AndroidTesterHelper(
            testProjectDir,
            classpathString,
            "com.guidovezzoni.smartproperties"
        )
    }

    @Test
    fun `auto-test simple gradle project`() {
        val autoTestAndroidTesterHelper = AndroidTesterHelper(testProjectDir)
        autoTestAndroidTesterHelper.writeAndroidProject(Type.GROOVY_SIMPLE)

        val result = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withArguments("assemble")
            .build()
    }

    @Test
    fun `auto-test buildscript android project`() {
        val autoTestAndroidTesterHelper = AndroidTesterHelper(testProjectDir)
        autoTestAndroidTesterHelper.writeAndroidProject(Type.GROOVY_BUILDSCRIPT_ANDROID)

        val result = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
//            .withArguments("assemble")  //requires SDK
            .withArguments("androidDependencies")
            .build()
    }

    @Test
    fun `auto-test plugins android project`() {
        val autoTestAndroidTesterHelper = AndroidTesterHelper(testProjectDir)
        autoTestAndroidTesterHelper.writeAndroidProject(Type.GROOVY_PLUGINS_ANDROID)

        val result = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
//            .withArguments("assemble")  //requires SDK
            .withArguments("androidDependencies")
            .build()
    }

    @Test
    fun `when not an android projects then fails`() {
        androidTesterHelper.writeAndroidProject(Type.GROOVY_SIMPLE)

        val result = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withArguments("assemble")
            .withPluginClasspath()
            .buildAndFail()
    }

    @Disabled
    @Test
    fun `when double default config found then fail`() {
        androidTesterHelper.writeAndroidProject(Type.GROOVY_BUILDSCRIPT_ANDROID)

        val result = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
//            .withArguments("assemble")  //requires SDK
            .withArguments("androidDependencies", "--stacktrace", "--scan")
//            .withPluginClasspath() // auto
//            .withPluginClasspath(pluginClassPathFiles)
            .withDebug(true)
//            .withEnvironment(mapOf("ANDROID_SDK_ROOT" to "/Users/guido/Library/Android/sdk"))
            .build()

    }

    @Disabled
    @Test
    fun `test helloWorld task`() {
        settingsFile.writeText(
            """
            rootProject.name = "hello-world"
        """.trimIndent()
        )
        rootGradleFile.writeText(
            """
            tasks.register("helloWorld") {
                doLast {
                    println("Hello world!")
                }
            }
        """.trimIndent()
        )

        val result = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withArguments("helloWorld")
            .build()

        assertTrue(result.output.contains("Hello world!"))
        assertEquals(TaskOutcome.SUCCESS, result.task(":helloWorld")?.outcome)
    }
}
