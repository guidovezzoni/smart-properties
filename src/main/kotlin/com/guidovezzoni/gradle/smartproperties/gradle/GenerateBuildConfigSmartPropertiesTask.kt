package com.guidovezzoni.gradle.smartproperties.gradle

import com.android.build.gradle.internal.dsl.ProductFlavor
import com.guidovezzoni.gradle.smartproperties.extensions.buildConfigFieldString
import com.guidovezzoni.gradle.smartproperties.gradle.base.SmartPropertiesBaseTask
import com.guidovezzoni.gradle.smartproperties.model.Type

open class GenerateBuildConfigSmartPropertiesTask : SmartPropertiesBaseTask() {
    override fun performFlavorOperation(
        productFlavor: ProductFlavor,
        key: String,
        doubleQuotedValue: String,
        types: Set<Type>
    ) {
        if (types.contains(Type.BUIILDCONFIG)) {
            productFlavor.buildConfigFieldString(key, doubleQuotedValue)
            logger.debug("BUILDCONFIG entry added $key")
        }
    }
}
