package com.guidovezzoni.gradle.smartproperties.gradle

import com.android.build.gradle.internal.dsl.ProductFlavor
import com.guidovezzoni.gradle.smartproperties.gradle.base.SmartPropertiesBaseTask
import com.guidovezzoni.gradle.smartproperties.model.Type

class GenerateProjectExtSmartProperties : SmartPropertiesBaseTask() {
    override fun performFlavorOperation(
        productFlavor: ProductFlavor,
        key: String,
        doubleQuotedValue: String,
        types: Set<Type>
    ) {
        if (types.contains(Type.PROJECT_EXT)) {
            project.extensions.extraProperties.set(key, doubleQuotedValue)
            logger.debug("PROJECT EXT entry added $key")
        }

        if (types.contains(Type.ROOT_EXT)) {
            project.rootProject.extensions.extraProperties.set(key, doubleQuotedValue)
            logger.debug("ROOT PROJECT EXT entry added $key")
        }
    }
}
