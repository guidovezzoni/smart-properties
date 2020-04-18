package com.guidovezzoni.gradle.smartproperties.exceptions

class InvalidConfigurationException : BaseGradleException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable?) : super(message, cause)
}
