package com.guidovezzoni.gradle.smartproperties.extensions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class StringsExtensionsKtTest {

    @Test
    fun `when contains BuildConfig token then isBuildConfigProperty returns true`() {
        val actualValue = "Property1.BuildConfig".isBuildConfigProperty()

        assertEquals(true, actualValue)
    }

    @Test
    fun `when does not contains BuildConfig token then isBuildConfigProperty returns false`() {
        val actualValue = "Property1.Buildonfig".isBuildConfigProperty()

        assertEquals(false, actualValue)
    }

    @Test
    fun `when contains Resources token then isResourcesProperty returns true`() {
        val actualValue = "Property1.Resources".isResourcesProperty()

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

    @Disabled
    @Test
    fun `when property has known tokens then hasKnownTokens returns true`() {
        var actualValue = "Property1.BuildConfig".hasKnownTokens()
        assertEquals(true, actualValue)

        actualValue = "Property1.Resources".hasKnownTokens()
        assertEquals(true, actualValue)
    }

    @Disabled
    @Test
    fun `when property has unknown tokens then hasKnownTokens returns false`() {
        val actualValue = "Property1.Unknown".hasKnownTokens()

        assertEquals(false, actualValue)
    }

    @Disabled
    @Test
    fun `when property doesnt have tokens then hasKnownTokens returns false`() {
        val actualValue = "Property1".hasKnownTokens()

        assertEquals(false, actualValue)
    }

    @Disabled
    @Test
    fun `when property has unknown tokens then hasUnknownTokens returns true`() {
        val actualValue = "Property1.Unknown.BuildConfig".hasUnknownTokens()

        assertEquals(true, actualValue)
    }

    @Disabled
    @Test
    fun `when property has known tokens then hasUnknownTokens returns false`() {
        var actualValue = "Property1.BuildConfig".hasUnknownTokens()
        assertEquals(false, actualValue)

        actualValue = "Property1.Resources".hasUnknownTokens()
        assertEquals(false, actualValue)
    }

    @Disabled
    @Test
    fun `when property has no tokens then hasUnknownTokens returns false`() {
        val actualValue = "Property1".hasUnknownTokens()

        assertEquals(false, actualValue)
    }

    @Test
    fun `when property has tokens cleanUpTokens removes them`() {
        val actualValue = "Property1.Resources.BuildConfig.ProjectExt.RootProjExt".cleanUpTokens()

        assertEquals("Property1", actualValue)
    }
}
