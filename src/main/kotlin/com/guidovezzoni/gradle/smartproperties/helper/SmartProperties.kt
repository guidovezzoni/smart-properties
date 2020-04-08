package com.guidovezzoni.gradle.smartproperties.helper

import com.guidovezzoni.gradle.smartproperties.extensions.*
import com.guidovezzoni.gradle.smartproperties.logger.CustomLogging
import com.guidovezzoni.gradle.smartproperties.model.Type
import java.io.File
import java.util.*

class SmartProperties : HashMap<String, Pair<String, Set<Type>>>() {
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

            if (!keyString.hasTokens()) {
                throw Exception("Keystring $keyString doesn't have any token")
            }

//            TODO  to be implemented
//            if (keyString.hasUnknownTokens()){
//                throw Exception("Keystring $keyString has unknown tokens")
//            }


//    TODO                     val finalValue = getEnvVar(keyString.cleanTokensUp()) ?: valueString

            val type: MutableSet<Type> = mutableSetOf()

            if (keyString.isBuildConfigProperty()) {
                type.add(Type.BUIILDCONFIG)
            }

            if (keyString.isResourcesProperty()) {
                type.add(Type.RESOURCES)
            }

            put(
                keyString.cleanTokensUp(),
                Pair(valueString.doubleQuoted(), type)
            )
        }
    }
}
