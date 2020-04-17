package com.guidovezzoni.gradle.smartproperties.gradle

import com.guidovezzoni.gradle.smartproperties.exceptions.InvalidConfigurationException
import groovy.lang.Closure
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.*
import java.io.File

internal open class ConfigScriptExtensionTest {
    lateinit var sut: ConfigScriptExtension

    @BeforeEach
    internal fun setUp() {
        val project = ProjectBuilder.builder().build()
        sut = ConfigScriptExtension(project)
    }

    @Test
    fun `defaultConfig() when a default config is already present`() {
        val configScriptBlock = ConfigScriptBlock("pre-existing")
        sut.defaultConfig = configScriptBlock

        val exception = assertThrows<InvalidConfigurationException> {
            val defaultConfig = sut.defaultConfig(Closure.IDENTITY)
        }

        Assertions.assertEquals("Only one defaultConfig closure allowed", exception.message)
    }

    @Disabled("Not sure how to play with a Closure yet")
    @Test
    fun `defaultConfig()`() {

    }

    @Test
    fun `getDefaultConfigSourceFile without default block`() {
        val actualFile = sut.getDefaultConfigSourceFile()

        Assertions.assertEquals("smart.properties", actualFile.name)
    }

    @Test
    fun `getDefaultConfigSourceFile with default block`() {
        val configScriptBlock = ConfigScriptBlock("name")
        configScriptBlock.sourceFile = File("in-existent file")
        sut.defaultConfig = configScriptBlock

        val actualFile = sut.getDefaultConfigSourceFile()

        Assertions.assertEquals("in-existent file", actualFile.name)
    }

    @Test
    fun `getDefaultConfigCiEnvironmentPrefix without default block`() {
        val actualPrefix = sut.getDefaultConfigCiEnvironmentPrefix()

        Assertions.assertEquals("", actualPrefix)
    }

    @Test
    fun `getDefaultConfigCiEnvironmentPrefix with default block`() {
        val configScriptBlock = ConfigScriptBlock("name")
        configScriptBlock.ciEnvironmentPrefix = "a prefix"
        sut.defaultConfig = configScriptBlock

        val actualPrefix = sut.getDefaultConfigCiEnvironmentPrefix()

        Assertions.assertEquals("a prefix", actualPrefix)
    }
}
