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
        val actualValue = "Property1.Resources.BuildConfig".cleanUpTokens()

        assertEquals(PROPERTY_1, actualValue)
    }

    @Test
    fun `addUnderscoreBetweenWords adds an underscore between a lowercase letter is followed by a uppercase letter`() {
        assertEquals("test_Property", "testProperty".addUnderscoreBetweenWords())
        assertEquals("property", "property".addUnderscoreBetweenWords())
    }

    @Test
    fun `addUnderscoreBetweenWords adds an underscore between a letter and a number`() {
        // lowercase
        assertEquals("test_Property_01", "testProperty01".addUnderscoreBetweenWords())
        // uppercase
        assertEquals("test_Propert_Y_01", "testPropertY01".addUnderscoreBetweenWords())
    }

    @Test
    fun `addUnderscoreBetweenWords adds an underscore between a number and a letter`() {
        assertEquals("01_Property", "01Property".addUnderscoreBetweenWords())
        assertEquals("01_property", "01property".addUnderscoreBetweenWords())
    }

    @Test
    fun `addUnderscoreBetweenWords - corner case - existing underscores are left unchanged`() {
        // corner case - existing underscore
        assertEquals("test_Property", "test_Property".addUnderscoreBetweenWords())
        // corner case - existing underscore
        assertEquals("test_Property_01", "testProperty_01".addUnderscoreBetweenWords())
        // corner case - existing underscore
        assertEquals("test_Propert_Y_01", "testPropertY_01".addUnderscoreBetweenWords())
        // corner case - existing underscore
        assertEquals("01_Property", "01_Property".addUnderscoreBetweenWords())
        // corner case - existing underscore
        assertEquals("01_property", "01_property".addUnderscoreBetweenWords())

    }

    @Test
    fun `addUnderscoreBetweenWords - corner case - multiple underscores are left unchanged`() {
        assertEquals("test__Property_2_underscore", "test__Property2underscore".addUnderscoreBetweenWords())
        assertEquals("test___Property_3_Underscore", "test___Property3Underscore".addUnderscoreBetweenWords())
    }

    @Test
    fun `addUnderscoreBetweenWords - corner case - multiple uppercase letters are left unchanged`() {
        assertEquals("TEST_PROPERTY", "TEST_PROPERTY".addUnderscoreBetweenWords())
    }

    @Test
    fun `toXmlSyntax returns underscores and lowercase`() {
        assertEquals("test_property_01_prop", "testProperty01prop".toXmlSyntax())
    }

    @Test
    fun `toConstantSyntax returns underscores and uppercase`() {
        assertEquals("TEST_PROPERTY_01_PROP", "testProperty01prop".toConstantSyntax())
    }

    companion object {
        const val PROPERTY_1 = "Property1"
        const val PROPERTY_2_BUILD_CONFIG = "Property2.BuildConfig"
        const val PROPERTY_3_RESOURCES = "Property3.Resources"
    }
}
