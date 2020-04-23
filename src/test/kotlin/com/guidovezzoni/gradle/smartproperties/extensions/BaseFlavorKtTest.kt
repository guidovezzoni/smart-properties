package com.guidovezzoni.gradle.smartproperties.extensions

import com.android.build.gradle.internal.dsl.BaseFlavor
import com.android.build.gradle.internal.dsl.ProductFlavor
import com.android.build.gradle.internal.errors.DeprecationReporter
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.gradle.api.Project
import org.gradle.api.logging.Logger
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class BaseFlavorKtTest {
    private lateinit var project: Project
    private lateinit var baseFlavourSpy: BaseFlavor

    @Suppress("UnstableApiUsage", "RemoveExplicitTypeArguments")
    @BeforeEach
    internal fun setUp() {
        project = ProjectBuilder.builder().build()
        baseFlavourSpy = spyk(
            ProductFlavor(
                "flavorName",
                project,
                project.objects,
                mockk<DeprecationReporter>(),
                mockk<Logger>()
            )
        )
    }

    @Test
    fun `when buildConfigFieldString ignoring syntax is called buildConfigField is called`() {
        baseFlavourSpy.buildConfigFieldString(KEY, VALUE, true)

        verify { baseFlavourSpy.buildConfigField("String", KEY, VALUE) }
    }

    @Test
    fun `when buildConfigFieldString with syntax is called buildConfigField is called`() {
        baseFlavourSpy.buildConfigFieldString(KEY, VALUE, false)

        verify { baseFlavourSpy.buildConfigField("String", KEY.toConstantSyntax(), VALUE) }
    }

    @Test
    fun `when resValueString is called resValue is called`() {
        baseFlavourSpy.resValueString(KEY, VALUE)

        verify { baseFlavourSpy.resValue("string", KEY, VALUE) }
    }

    companion object {
        const val KEY = "key"
        const val VALUE = "value"
    }
}
