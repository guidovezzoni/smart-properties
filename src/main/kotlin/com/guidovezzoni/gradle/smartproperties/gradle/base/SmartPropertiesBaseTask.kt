package com.guidovezzoni.gradle.smartproperties.gradle.base

import com.android.build.gradle.internal.dsl.ProductFlavor
import com.guidovezzoni.gradle.smartproperties.extensions.getAndroid
import com.guidovezzoni.gradle.smartproperties.helper.SmartProperties
import com.guidovezzoni.gradle.smartproperties.logger.CustomLogging
import com.guidovezzoni.gradle.smartproperties.model.Type
import org.gradle.api.DefaultTask
import org.gradle.api.logging.Logger
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

abstract class SmartPropertiesBaseTask : DefaultTask() {
    @get:Internal
    internal lateinit var entries: SmartProperties

    @get:Internal
    internal lateinit var flavorName: String

    init {
        replaceLogger(CustomLogging.getLogger(SmartPropertiesBaseTask::class.java))
    }

    abstract fun performFlavorOperation(
        productFlavor: ProductFlavor,
        key: String,
        doubleQuotedValue: String,
        types: Set<Type>
    )

    @TaskAction
    fun performAction() {
        val android = project.getAndroid()

        entries.forEach { entries ->

            val productFlavor = android.productFlavors.find { productFlavor ->
                productFlavor.name == flavorName
            }

            productFlavor?.let { flavor ->
                performFlavorOperation(flavor, entries.key, entries.value.first, entries.value.second)
            }
        }
    }
}
