package com.guidovezzoni.gradle.smartproperties.extensions

import com.android.build.gradle.AppExtension
import org.gradle.api.Project

fun Project.getAndroid(): AppExtension {
    return this.extensions.findByName("android") as AppExtension?
        ?: throw Exception("Not an Android application")
}
