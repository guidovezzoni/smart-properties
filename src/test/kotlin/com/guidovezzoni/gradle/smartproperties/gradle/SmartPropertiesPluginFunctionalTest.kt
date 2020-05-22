package com.guidovezzoni.gradle.smartproperties.gradle

import com.guidovezzoni.gradle.smartproperties.gradle.androidplugintest.AndroidTesterHelper
import com.guidovezzoni.gradle.smartproperties.gradle.androidplugintest.Type
import org.gradle.internal.impldep.org.junit.Rule
import org.gradle.internal.impldep.org.junit.rules.TemporaryFolder
import org.gradle.testkit.runner.BuildResult
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

        androidTesterHelper = AndroidTesterHelper(testProjectDir, classpathString)
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

    @Test
    internal fun `functional test`() {
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

    @Test
    internal fun `when not an android module then fail`() {

    }

    @Disabled
    @Test
    internal fun `can successfully configure URL through extension and verify it`() {
        settingsFile.writeText("rootProject.name = 'hello-world'")

        rootGradleFile.writeText("")

        val result: BuildResult = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withArguments("helloWorld")
            .build()

    }
}
