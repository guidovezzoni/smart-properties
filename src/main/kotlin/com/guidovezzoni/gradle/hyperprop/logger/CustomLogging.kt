package com.guidovezzoni.gradle.hyperprop.logger

import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging

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
