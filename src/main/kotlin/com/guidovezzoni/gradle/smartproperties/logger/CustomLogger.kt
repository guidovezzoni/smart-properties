package com.guidovezzoni.gradle.smartproperties.logger

import org.gradle.api.logging.Logger

class CustomLogger(logger: Logger) : Logger by logger {
    override fun debug(message: String?) {
        // TODO remove this / handle differently
         quiet("ooooooooooooooo $message")
    }
}
