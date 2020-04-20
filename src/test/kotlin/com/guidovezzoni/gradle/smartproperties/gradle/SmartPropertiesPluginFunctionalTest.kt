package com.guidovezzoni.gradle.smartproperties.gradle

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

    lateinit var buildFile: File
    lateinit var settingsFile: File


    @BeforeEach
    internal fun setUp() {
        testProjectDir.create()
        buildFile = testProjectDir.newFile("build.gradle")
        settingsFile = testProjectDir.newFile("settings.gradle")
    }

    @Test
    fun `test helloWorld task`() {
        settingsFile.writeText(
            """
            rootProject.name = "hello-world"
        """.trimIndent()
        )
        buildFile.writeText(
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
        settingsFile.writeText("rootProject.name = \"test-project\"\n")

//        buildFile.writeText("plugins {\nid(\"com.guidovezzoni.smartproperties\") version \"0.4.1-local\"\n}\n")
        buildFile.writeText(
            """
                buildscript {
                    dependencies {
                        classpath("com.android.tools.build:gradle:3.2.1")
                        classpath(kotlin("gradle-plugin", version = "1.3.72"))
                    }
                }
                
            plugins{
                id("com.guidovezzoni.smartproperties") version "0.4.1-local"
                id("com.android.application")
                kotlin("android")
            }
        """.trimIndent()
        )


        val result = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withArguments("testProject")
            .withPluginClasspath()
            .build()

    }

    @Disabled
    @Test
    internal fun `can successfully configure URL through extension and verify it`() {
        settingsFile.writeText("rootProject.name = 'hello-world'")

        buildFile.writeText("")

        val result: BuildResult = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withArguments("helloWorld")
            .build()

    }
}