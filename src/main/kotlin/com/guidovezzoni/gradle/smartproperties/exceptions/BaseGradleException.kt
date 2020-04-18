package com.guidovezzoni.gradle.smartproperties.exceptions

import org.gradle.api.GradleException

open class BaseGradleException : GradleException {
        constructor() : super()
        constructor(message: String) : super(message)
        constructor(message: String, cause: Throwable?) : super(message, cause)
}
