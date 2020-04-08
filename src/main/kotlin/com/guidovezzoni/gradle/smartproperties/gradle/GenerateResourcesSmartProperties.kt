package com.guidovezzoni.gradle.smartproperties.gradle

import com.android.build.gradle.internal.dsl.ProductFlavor
import com.guidovezzoni.gradle.smartproperties.extensions.resValueStringIfRequired
import com.guidovezzoni.gradle.smartproperties.gradle.base.SmartPropertiesBaseTask

open class GenerateResourcesSmartProperties : SmartPropertiesBaseTask() {
    override fun performFlavorOperation(productFlavor: ProductFlavor, key: String, doubleQuotedValue: String) {
        productFlavor.resValueStringIfRequired(key, doubleQuotedValue)
    }
}
