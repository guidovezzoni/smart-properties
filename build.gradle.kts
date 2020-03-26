repositories {
    mavenCentral()
    jcenter()
}

plugins {
    kotlin("jvm") version "1.3.70"
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "0.10.1"
    `maven-publish`
}

dependencies {
    implementation("com.android.tools.build:gradle:3.6.1")

//    testCompile("junit", "junit", "4.12")
}

allprojects {
    repositories {
        google()
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

gradlePlugin {
    plugins {
        create("smartPropertiesPlugin") {
            id = "com.guidovezzoni.smartproperties"
            group = "com.guidovezzoni.smartproperties"
            version = "0.2.1-beta"
            displayName = "Smart Properties Plugin"
            description = "Manage build parameters from a *.property file or environment variables"
            implementationClass = "com.guidovezzoni.gradle.smartproperties.gradle.SmartPropertiesPlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/guidovezzoni/smart-properties"
    vcsUrl = "https://github.com/guidovezzoni/smart-properties.git"
    tags = listOf("properties", "environment variables", "android", "buildconfig", "resources")
}
