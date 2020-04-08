package com.guidovezzoni.gradle.smartproperties.gradle.base

import com.android.build.gradle.internal.dsl.ProductFlavor
import com.guidovezzoni.gradle.smartproperties.extensions.doubleQuoted
import com.guidovezzoni.gradle.smartproperties.extensions.getAndroid
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import java.util.*

abstract class SmartPropertiesBaseTask : DefaultTask() {
    @get:Internal
    internal lateinit var entries: Properties

    @get:Internal
    internal lateinit var flavorName: String

    abstract fun performFlavorOperation(productFlavor: ProductFlavor, key: String, doubleQuotedValue: String)

    @TaskAction
    fun performAction() {
        val android = project.getAndroid()

        entries.forEach { propKey, propValue ->
            val keyString = propKey.toString()
            val valueString = propValue.toString()

//                        val finalValue = getEnvVar(keyString.cleanTokensUp()) ?: valueString
            val escapedValue = valueString.doubleQuoted()

            val productFlavor = android.productFlavors.find { productFlavor ->
                productFlavor.name == flavorName
            }

            productFlavor?.let { flavor ->
                performFlavorOperation(flavor, keyString, escapedValue)
            }
        }
    }
}
