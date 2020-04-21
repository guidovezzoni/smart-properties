package com.guidovezzoni.gradle.smartproperties.gradle

import com.android.build.gradle.internal.dsl.ProductFlavor
import com.guidovezzoni.gradle.smartproperties.extensions.resValueString
import com.guidovezzoni.gradle.smartproperties.gradle.base.SmartPropertiesBaseTask
import com.guidovezzoni.gradle.smartproperties.model.Type
import com.guidovezzoni.gradle.smartproperties.model.VariantInfo

open class GenerateResourcesSmartPropertiesTask : SmartPropertiesBaseTask() {
    override fun performFlavorOperation(
        productFlavor: ProductFlavor,
        variantInfo: VariantInfo,
        key: String,
        doubleQuotedValue: String,
        types: Set<Type>
    ) {
        if (types.contains(Type.RESOURCES)) {
            productFlavor.resValueString(key, doubleQuotedValue)
            logger.debug("RESVALUE String added $key")
        }
    }
}
