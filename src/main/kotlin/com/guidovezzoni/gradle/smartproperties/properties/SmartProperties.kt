package com.guidovezzoni.gradle.smartproperties.properties

import com.guidovezzoni.gradle.smartproperties.extensions.*
import com.guidovezzoni.gradle.smartproperties.logger.CustomLogging
import com.guidovezzoni.gradle.smartproperties.model.Type
import java.io.File
import java.util.*

/**
 * This class handles the logic behind the smart-properties and builds a specific HashMap to handle them
 */
class SmartProperties(private val ciPrefix: String) : HashMap<String, Pair<String, Set<Type>>>() {
    private val logger = CustomLogging.getLogger(SmartProperties::class.java)

    fun load(file: File?) {
        if (file?.exists() != true) {
            throw IllegalArgumentException("No valid file for parameter sourceFile - ${file?.absoluteFile} not found")
        }
        val properties = Properties()
        properties.load(file.reader())

        properties.forEach { propKey, propValue ->
            val keyString = propKey.toString()
            val valueString = propValue.toString()

            checkTokens(keyString)
            addSmartProperty(keyString, valueString)
        }
    }

    private fun checkTokens(keyString: String) {
        if (!keyString.hasKnownTokens()) {
            throw IllegalArgumentException("Key $keyString doesn't have any token")
        }

        if (keyString.hasUnknownTokens()) {
            throw IllegalArgumentException("Key $keyString has unknown tokens")
        }
    }

    private fun addSmartProperty(key: String, value: String) {
        val finalValue = getEnvVar(key.cleanUpTokens(), ciPrefix) ?: value

        val type: MutableSet<Type> = mutableSetOf()
        if (key.isBuildConfigProperty()) {
            type.add(Type.BUIILDCONFIG)
        }
        if (key.isResourcesProperty()) {
            type.add(Type.RESOURCES)
        }

        put(
            key.cleanUpTokens(),
            Pair(finalValue.doubleQuoted(), type)
        )
    }

    private fun getEnvVar(propName: String, ciPrefix: String): String? {
        val ciPropName = ciPrefix + propName
        val ciEnvVar = System.getenv(ciPropName)
        ciEnvVar?.let {
            logger.debug("Property $propName has been overridden ($ciPropName)")
            return ciEnvVar
        }

        val envVar = System.getenv(propName)
        envVar?.let {
            logger.debug("Property $propName has been overridden")
            return envVar
        }

        return null
    }
}
