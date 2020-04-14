package com.guidovezzoni.gradle.smartproperties.extensions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StringsKtTest {

    @Test
    fun `when contains BuildConfig token then isBuildConfigProperty returns true`() {
        val actualValue = PROPERTY_2_BUILD_CONFIG.isBuildConfigProperty()

        assertEquals(true, actualValue)
    }

    @Test
    fun `when does not contains BuildConfig token then isBuildConfigProperty returns false`() {
        val actualValue = "Property1.Buildonfig".isBuildConfigProperty()

        assertEquals(false, actualValue)
    }

    @Test
    fun `when contains Resources token then isResourcesProperty returns true`() {
        val actualValue = PROPERTY_3_RESOURCES.isResourcesProperty()

        assertEquals(true, actualValue)
    }

    @Test
    fun `when does not Resources BuildConfig token then isResourcesProperty returns false`() {
        val actualValue = "Property1.Resource".isResourcesProperty()

        assertEquals(false, actualValue)
    }

    @Test
    fun `check doubleQuoted returns correct string`() {
        val actualValue = "test".doubleQuoted()

        assertEquals("\"test\"", actualValue)
    }

    @Test
    fun `when property has known tokens then hasKnownTokens returns true`() {
        var actualValue = PROPERTY_2_BUILD_CONFIG.hasKnownTokens()
        assertEquals(true, actualValue)

        actualValue = PROPERTY_3_RESOURCES.hasKnownTokens()
        assertEquals(true, actualValue)
    }

    @Test
    fun `when property has unknown tokens then hasKnownTokens returns false`() {
        val actualValue = "Property1.Unknown".hasKnownTokens()

        assertEquals(false, actualValue)
    }

    @Test
    fun `when property doesnt have tokens then hasKnownTokens returns false`() {
        val actualValue = PROPERTY_1.hasKnownTokens()

        assertEquals(false, actualValue)
    }

    @Test
    fun `when property has unknown tokens then hasUnknownTokens returns true`() {
        val actualValue = "Property1.Unknown.BuildConfig".hasUnknownTokens()

        assertEquals(true, actualValue)
    }

    @Test
    fun `when property has known tokens then hasUnknownTokens returns false`() {
        var actualValue = PROPERTY_2_BUILD_CONFIG.hasUnknownTokens()
        assertEquals(false, actualValue)

        actualValue = PROPERTY_3_RESOURCES.hasUnknownTokens()
        assertEquals(false, actualValue)
    }

    @Test
    fun `when property has no tokens then hasUnknownTokens returns false`() {
        val actualValue = PROPERTY_1.hasUnknownTokens()

        assertEquals(false, actualValue)
    }

    @Test
    fun `when property has tokens cleanUpTokens removes them`() {
//        val actualValue = "Property1.Resources.BuildConfig.ProjectExt.RootProjExt".cleanUpTokens()
        val actualValue = "Property1.Resources.BuildConfig".cleanUpTokens()

        assertEquals(PROPERTY_1, actualValue)
    }

    companion object {
        const val PROPERTY_1 = "Property1"
        const val PROPERTY_2_BUILD_CONFIG = "Property2.BuildConfig"
        const val PROPERTY_3_RESOURCES = "Property3.Resources"
    }
}
