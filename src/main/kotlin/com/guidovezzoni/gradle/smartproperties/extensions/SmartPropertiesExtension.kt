package com.guidovezzoni.gradle.smartproperties.extensions

import com.android.build.gradle.api.ApplicationVariant
import com.guidovezzoni.gradle.smartproperties.gradle.SmartPropertiesExtension
import com.guidovezzoni.gradle.smartproperties.model.VariantInfo

fun SmartPropertiesExtension.toVariantInfo(androidVariant: ApplicationVariant): VariantInfo {
    val variantInfo = VariantInfo(variantName = androidVariant.name)

    productFlavors?.forEach { productFlavor ->
        if (productFlavor.name.equals(androidVariant.flavorName))
            variantInfo.sourceFile = productFlavor.sourceFile
    }
    if (variantInfo.sourceFile == null) {
        variantInfo.sourceFile = getDefaultConfigSourceFile()
    }

    productFlavors?.forEach { productFlavor ->
        if (productFlavor.name.equals(androidVariant.flavorName))
            variantInfo.ciEnvironmentPrefix = productFlavor.ciEnvironmentPrefix
    }
    if (variantInfo.ciEnvironmentPrefix == null) {
        variantInfo.ciEnvironmentPrefix = getDefaultConfigCiEnvironmentPrefix()
    }

    return variantInfo
}
