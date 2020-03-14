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
    implementation("com.android.tools.build:gradle:3.6.0")

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
        create("hyperPropertiesPlugin") {
            id = "com.guidovezzoni.hyperprop"
            group = "com.guidovezzoni.hyperprop"
            version = "0.1.1"
            displayName = "Hyper Properties Plugin"
            description = "Manage build parameters from a *.property file or an environment variables"
            implementationClass = "com.guidovezzoni.gradle.hyperprop.gradle.HyperpropPlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/com.guidovezzoni/xxx"
    vcsUrl = "https://github.com/com.guidovezzoni/xxx.git"
    tags = listOf("properties", "environment variables", "android")
}
