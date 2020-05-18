package com.guidovezzoni.gradle.smartproperties.gradle.androidplugintest

import org.gradle.internal.impldep.org.junit.rules.TemporaryFolder

class AndroidTesterHelper(
    private val temporaryFolder: TemporaryFolder,
    private val injectedClassPath: String = ""
) {

    fun writeAndroidProject(type: Type) {
        temporaryFolder.create()
        val settingsFile = temporaryFolder.newFile("settings.gradle")
        val rootGradleFile = temporaryFolder.newFile("build.gradle")
        val gradleProperties = temporaryFolder.newFile("gradle.properties")

        temporaryFolder.newFolder("app")
        val appGradleFile = temporaryFolder.newFile("app/build.gradle")

        temporaryFolder.newFolder("app", "src", "main")
        val androidManifest = temporaryFolder.newFile("app/src/main/AndroidManifest.xml")

        gradleProperties.writeText(GRADLE_PROPERTIES_CONTENT)
        androidManifest.writeText(ANDROID_MANIFEST_CONTENT)

        when (type) {
            Type.GROOVY_CLASSPATH -> {
                settingsFile.writeText(SETTINGS_GRADLE_CONTENT)
                rootGradleFile.writeText(ROOT_GRADLE_CONTENT)
                appGradleFile.writeText(APP_GRADLE_CONTENT)
            }
            Type.GROOVY_PLUGINS -> {
                settingsFile.writeText(SETTINGS_GRADLE_CONTENT_2)
                rootGradleFile.writeText(ROOT_GRADLE_CONTENT_2)
                appGradleFile.writeText(APP_GRADLE_CONTENT_2)
            }
        }
    }

    //    companion object {
    private val getKotlinVersion: String
        get() = "1.3.72"


    val SETTINGS_GRADLE_CONTENT =
        """
                rootProject.name='SimplestAndroidProject2'
                include ':app'
            """.trimIndent()

    val SETTINGS_GRADLE_CONTENT_2 =
        """
pluginManagement {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "com.android.application") {
                useModule("com.android.tools.build:gradle:3.6.1")
            }
        }
    }
}

rootProject.name='simplestandroidproject2'
include ':app'
            """.trimIndent()

    val ROOT_GRADLE_CONTENT =
        """
// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        mavenCentral()
        google()
        jcenter()
        maven {
            url "https://plugins.gradle.org/m2/" 
        }
        
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.6.3'
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$getKotlinVersion"
//        classpath "com.guidovezzoni.smartproperties:smart-properties:0.5.1-beta"
        classpath files($injectedClassPath)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
 
allprojects {
    repositories {
        google()
        jcenter()
        
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}
        """.trimIndent()

    val ROOT_GRADLE_CONTENT_2 =
        """
// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id 'com.android.application' apply false
    id 'org.jetbrains.kotlin.jvm' version '1.3.72' apply false
    id 'org.jetbrains.kotlin.android'  version '1.3.72' apply false
    //id 'org.jetbrains.kotlin.android.extensions'  version '1.3.72' apply false
//    id "com.guidovezzoni.smartproperties" version "0.5.1-beta" apply false
    id "com.guidovezzoni.smartproperties" apply false
}

allprojects {
    repositories {
        google()
        jcenter()
        
    }
}

//task clean(type: Delete) {
//    delete rootProject.buildDir
//}
            """.trimIndent()

    val GRADLE_PROPERTIES_CONTENT =
        """
android.useAndroidX=true
        """.trimIndent()

    val APP_GRADLE_CONTENT =
        """
apply plugin: 'com.android.application'
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'
apply plugin: 'com.guidovezzoni.smartproperties'

android {
    compileSdkVersion 29
    buildToolsVersion "29.0.2"

    defaultConfig {
        applicationId "com.example.simplestandroidproject2"
        minSdkVersion 16
        targetSdkVersion 29
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }

}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$getKotlinVersion"
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.core:core-ktx:1.2.0'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
}   
            """.trimIndent()

    val APP_GRADLE_CONTENT_2 =
        """
//apply plugin: 'com.android.application'
//apply plugin: 'kotlin-android'
//apply plugin: 'kotlin-android-extensions'

plugins {
    id('com.android.application')
    id ('org.jetbrains.kotlin.android')
//    id ('org.jetbrains.kotlin.android.extensions')
    id "com.guidovezzoni.smartproperties"
}

android {
    compileSdkVersion 29
    buildToolsVersion "29.0.2"

    defaultConfig {
        applicationId "com.example.simplestandroidproject2"
        minSdkVersion 16
        targetSdkVersion 29
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }

}

dependencies {
    //implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.72"
//    implementation 'androidx.appcompat:appcompat:1.1.0'
//    implementation 'androidx.core:core-ktx:1.2.0'
//    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
//    testImplementation 'junit:junit:4.12'
//    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
//    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
}
            """.trimIndent()

    val ANDROID_MANIFEST_CONTENT =
        """
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.simplestandroidproject2">

    <application
        android:allowBackup="true"
        android:label="simplestandroidproject2"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>                
            """.trimIndent()
//    }
}