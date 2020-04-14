package com.guidovezzoni.gradle.smartproperties.extensions

import com.android.build.gradle.AppExtension
import com.guidovezzoni.gradle.smartproperties.exceptions.NotAnAndroidModuleException
import org.gradle.api.Project

fun Project.getAndroid(): AppExtension {
    return this.extensions.findByName("android") as AppExtension?
        ?: throw NotAnAndroidModuleException("Not an Android application")
}
