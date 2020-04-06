package com.guidovezzoni.gradle.smartproperties.logger

import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging

@Suppress("unused")
class CustomLogging {

    companion object {
        fun getLogger(c: Class<*>): Logger {
            return Logging.getLogger(c)
        }

        fun getLogger(name: String): Logger {
            return Logging.getLogger(name)
        }
    }
}
