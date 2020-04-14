package com.guidovezzoni.gradle.smartproperties.logger

import org.gradle.api.logging.Logger

/**
 * Purpose of this class is to provide a logger that logs debug info to quiet.
 * Might be removed  later on
 */
class CustomLogger(logger: Logger) : Logger by logger {
    override fun debug(message: String?) {
        quiet("$message")
    }
}
