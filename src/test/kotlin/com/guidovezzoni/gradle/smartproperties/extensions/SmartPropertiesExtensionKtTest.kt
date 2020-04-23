package com.guidovezzoni.gradle.smartproperties.extensions

import com.android.build.gradle.internal.api.ApplicationVariantImpl
import com.guidovezzoni.gradle.smartproperties.gradle.ConfigScriptBlock
import com.guidovezzoni.gradle.smartproperties.gradle.ConfigScriptExtension
import com.guidovezzoni.gradle.smartproperties.gradle.ConfigScriptExtensionTest
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.gradle.api.NamedDomainObjectContainer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.io.File

@ExtendWith(MockKExtension::class)
internal class SmartPropertiesExtensionKtTest : ConfigScriptExtensionTest() {
    @MockK
    lateinit var androidVariant: ApplicationVariantImpl

    lateinit var flavors: NamedDomainObjectContainer<ConfigScriptBlock>

    @BeforeEach
    override fun setUp() {
        super.setUp()
        every { androidVariant.flavorName } returns ANDROID_VARIANT_FLAVOR_NAME
        every { androidVariant.name } returns ANDROID_VARIANT_NAME

        flavors = project.container(ConfigScriptBlock::class.java)

        var aFlavor = flavors.create("alpha")
        aFlavor.sourceFile = File("alpha.properties")
        aFlavor.ciEnvironmentPrefix = "pref-alpha-"
        aFlavor.ignoreBuildConfigSyntax = true

        aFlavor = flavors.create("beta")
        aFlavor.sourceFile = File("beta.properties")
        aFlavor.ciEnvironmentPrefix = "pref-beta-"
        aFlavor.ignoreBuildConfigSyntax = true
    }

    @Test
    fun `when the requested variant and defaultConfig are NOT present then returns the default values`() {
        val variantInfo = sut.getConfigurationForVariant(androidVariant)

        assertEquals(ANDROID_VARIANT_NAME, variantInfo.androidVariantName)
        assertEquals("", variantInfo.productFlavorName)
        assertEquals(DEFAULT_FILENAME, variantInfo.sourceFile.name)
        assertEquals(DEFAULT_CI_ENV_PREFIX, variantInfo.ciEnvironmentPrefix)
        assertEquals(DEFAULT_IGNORE_BUILD_CONFIG_SYNTAX, variantInfo.ignoreBuildConfigSyntax)
    }

    @Test
    fun `when the requested variant is NOT present but defaultConfig is then returns defaultConfig values`() {
        sut.defaultConfig = defaultConfig

        val variantInfo = sut.getConfigurationForVariant(androidVariant)

        assertEquals(ANDROID_VARIANT_NAME, variantInfo.androidVariantName)
        assertEquals("", variantInfo.productFlavorName)
        assertEquals(DEFAULT_CONFIG_FILE, variantInfo.sourceFile.name)
        assertEquals(DEFAULT_CONFIG_PREFIX, variantInfo.ciEnvironmentPrefix)
        assertEquals(DEFAULT_CONFIG_IGNORE_SYNTAX, variantInfo.ignoreBuildConfigSyntax)
    }

    @Test
    fun `when both defaultConfig and requested variant (with values) are present then returns the variant values`() {
        sut.defaultConfig = defaultConfig

        val aFlavor = flavors.create(DEV_CONFIG_NAME)
        aFlavor.sourceFile = File(DEV_CONFIG_FILE)
        aFlavor.ciEnvironmentPrefix = DEV_CONFIG_PREFIX
        aFlavor.ignoreBuildConfigSyntax = DEV_CONFIG_IGNORE_SYNTAX
        sut.productFlavors = flavors

        val variantInfo = sut.getConfigurationForVariant(androidVariant)

        assertEquals(ANDROID_VARIANT_NAME, variantInfo.androidVariantName)
        assertEquals(DEV_CONFIG_NAME, variantInfo.productFlavorName)
        assertEquals(DEV_CONFIG_FILE, variantInfo.sourceFile.name)
        assertEquals(DEV_CONFIG_PREFIX, variantInfo.ciEnvironmentPrefix)
        assertEquals(DEV_CONFIG_IGNORE_SYNTAX, variantInfo.ignoreBuildConfigSyntax)
    }

    @Test
    fun `when both defaultConfig and requested variant (without values) are present then returns the defaultConfig`() {
        sut.defaultConfig = defaultConfig

        flavors.create(DEV_CONFIG_NAME)
        sut.productFlavors = flavors

        val variantInfo = sut.getConfigurationForVariant(androidVariant)

        assertEquals(ANDROID_VARIANT_NAME, variantInfo.androidVariantName)
        assertEquals(DEV_CONFIG_NAME, variantInfo.productFlavorName)
        assertEquals(DEFAULT_CONFIG_FILE, variantInfo.sourceFile.name)
        assertEquals(DEFAULT_CONFIG_PREFIX, variantInfo.ciEnvironmentPrefix)
        assertEquals(DEFAULT_CONFIG_IGNORE_SYNTAX, variantInfo.ignoreBuildConfigSyntax)
    }

    companion object {
        const val ANDROID_VARIANT_FLAVOR_NAME = "dev"
        const val ANDROID_VARIANT_NAME = "devDebug"

        const val DEV_CONFIG_NAME = "dev"
        const val DEV_CONFIG_FILE = "dev.properties"
        const val DEV_CONFIG_PREFIX = "pref-dev-"
        const val DEV_CONFIG_IGNORE_SYNTAX = true

        const val DEFAULT_FILENAME = "smart.properties"
        const val DEFAULT_CI_ENV_PREFIX = ""
        const val DEFAULT_IGNORE_BUILD_CONFIG_SYNTAX = false
    }
}
