package com.guidovezzoni.gradle.smartproperties.gradle

import com.android.build.gradle.internal.dsl.ProductFlavor
import com.guidovezzoni.gradle.smartproperties.extensions.buildConfigFieldString
import com.guidovezzoni.gradle.smartproperties.gradle.base.SmartPropertiesBaseTask
import com.guidovezzoni.gradle.smartproperties.model.Type
import com.guidovezzoni.gradle.smartproperties.model.VariantInfo

open class GenerateBuildConfigSmartPropertiesTask : SmartPropertiesBaseTask() {
    override fun performFlavorOperation(
        productFlavor: ProductFlavor,
        variantInfo: VariantInfo,
        key: String,
        doubleQuotedValue: String,
        types: Set<Type>
    ) {
        if (types.contains(Type.BUIILDCONFIG)) {
            productFlavor.buildConfigFieldString(key, doubleQuotedValue, variantInfo.dontRenameProperties)
            logger.debug("BUILDCONFIG entry added $key")
        }
    }
}
