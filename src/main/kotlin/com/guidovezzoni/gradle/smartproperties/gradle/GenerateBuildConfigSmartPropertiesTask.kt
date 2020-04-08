package com.guidovezzoni.gradle.smartproperties.gradle

import com.android.build.gradle.internal.dsl.ProductFlavor
import com.guidovezzoni.gradle.smartproperties.extensions.buildConfigFieldStringIfRequested
import com.guidovezzoni.gradle.smartproperties.gradle.base.SmartPropertiesBaseTask

open class GenerateBuildConfigSmartPropertiesTask : SmartPropertiesBaseTask() {
    override fun performFlavorOperation(productFlavor: ProductFlavor, key: String, doubleQuotedValue: String) {
        productFlavor.buildConfigFieldStringIfRequested(key, doubleQuotedValue)
    }
}
