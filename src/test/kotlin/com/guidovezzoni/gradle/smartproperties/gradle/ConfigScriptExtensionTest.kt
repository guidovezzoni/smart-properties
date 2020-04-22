package com.guidovezzoni.gradle.smartproperties.gradle

import groovy.lang.Closure
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import java.io.File

@ExtendWith(MockKExtension::class)
internal open class ConfigScriptExtensionTest {
    val project : Project = ProjectBuilder.builder().build()
    lateinit var sut: ConfigScriptExtension

    @MockK
    lateinit var defaultConfig: ConfigScriptBlock

    @BeforeEach
    internal open fun setUp() {
        sut = ConfigScriptExtension(project)

        every { defaultConfig.name } returns null
        every { defaultConfig.sourceFile } returns File(DEFAULT_CONFIG_FILE)
        every { defaultConfig.ciEnvironmentPrefix } returns DEFAULT_CONFIG_PREFIX
        every { defaultConfig.ignoreBuildConfigSyntax } returns DEFAULT_CONFIG_IGNORE_SYNTAX
    }

    @Test
    fun `defaultConfig() throws an exception when a default config is already present`() {
        sut.defaultConfig = defaultConfig

        val exception = assertThrows<IllegalArgumentException> {
            sut.defaultConfig(Closure.IDENTITY)
        }

        Assertions.assertEquals("Only one defaultConfig closure allowed", exception.message)
    }

    @Disabled("Not sure how to play with a Closure yet")
    @Test
    fun `defaultConfig()`() {
        TODO("To be implemented")
    }

    @Test
    fun `getDefaultConfigSourceFile without default block`() {
        val actualFile = sut.getDefaultConfigSourceFile()

        Assertions.assertEquals("smart.properties", actualFile.name)
    }

    @Test
    fun `getDefaultConfigSourceFile with default block`() {
        sut.defaultConfig = defaultConfig

        val actualFile = sut.getDefaultConfigSourceFile()

        Assertions.assertEquals(DEFAULT_CONFIG_FILE, actualFile.name)
    }

    @Test
    fun `getDefaultConfigCiEnvironmentPrefix without default block`() {
        val actualPrefix = sut.getDefaultConfigCiEnvironmentPrefix()

        Assertions.assertEquals("", actualPrefix)
    }

    @Test
    fun `getDefaultConfigCiEnvironmentPrefix with default block`() {
        sut.defaultConfig = defaultConfig

        val actualPrefix = sut.getDefaultConfigCiEnvironmentPrefix()

        Assertions.assertEquals(DEFAULT_CONFIG_PREFIX, actualPrefix)
    }

    companion object {
        const val DEFAULT_CONFIG_FILE = "default-config"
        const val DEFAULT_CONFIG_PREFIX = "default_config_prefix"
        const val DEFAULT_CONFIG_IGNORE_SYNTAX = false
    }
}
