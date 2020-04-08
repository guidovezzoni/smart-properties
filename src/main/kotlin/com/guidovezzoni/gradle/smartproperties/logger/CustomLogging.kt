package com.guidovezzoni.gradle.smartproperties.logger

import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging

class CustomLogging {

    companion object {
        private const val DEBUG = false

        @Suppress("ConstantConditionIf")
        fun getLogger(c: Class<*>): Logger {
            return if (DEBUG) {
                CustomLogger(Logging.getLogger(c))
            } else {
                Logging.getLogger(c)
            }
        }

        @Suppress("unused")
        fun getLogger(name: String): Logger {
            return Logging.getLogger(name)
        }
    }
}
