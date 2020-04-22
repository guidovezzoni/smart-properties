package com.guidovezzoni.gradle.smartproperties.extensions

import com.android.build.gradle.api.ApplicationVariant
import com.guidovezzoni.gradle.smartproperties.gradle.ConfigScriptExtension
import com.guidovezzoni.gradle.smartproperties.model.VariantInfo
import java.io.File

const val DEFAULT_FILENAME = "smart.properties"
const val DEFAULT_CI_ENV_PREFIX = ""
const val DEFAULT_IGNORE_BUILD_CONFIG_SYNTAX = false

fun ConfigScriptExtension.getConfigurationForVariant(androidVariant: ApplicationVariant): VariantInfo {
    var flavorName: String? = null
    var sourceFile: File? = null
    var ciEnvironmentPrefix: String? = null
    var ignoreBuildConfigSyntax: Boolean? = null

    productFlavors?.forEach { productFlavor ->
        if (productFlavor.name.equals(androidVariant.flavorName)) {
            logger.debug("A productFlavor has been found that matches the current Android variant flavorName (${androidVariant.flavorName})")
            flavorName = productFlavor.name
            sourceFile = productFlavor.sourceFile
            ciEnvironmentPrefix = productFlavor.ciEnvironmentPrefix
            ignoreBuildConfigSyntax = productFlavor.ignoreBuildConfigSyntax
        }
    }

    return VariantInfo(
        androidVariant.name,
        flavorName ?: "",
        sourceFile ?: defaultConfig?.sourceFile ?: File(DEFAULT_FILENAME),
        ciEnvironmentPrefix ?: defaultConfig?.ciEnvironmentPrefix ?: DEFAULT_CI_ENV_PREFIX,
        ignoreBuildConfigSyntax ?: defaultConfig?.ignoreBuildConfigSyntax ?: DEFAULT_IGNORE_BUILD_CONFIG_SYNTAX
    )
}
