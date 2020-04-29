import org.gradle.api.tasks.testing.logging.TestLogEvent

repositories {
    mavenCentral()
    jcenter()
}

plugins {
    kotlin("jvm") version "1.3.72"
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "0.11.0"
    `maven-publish`
}

dependencies {
    implementation("com.android.tools.build:gradle:3.6.1")

    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
    testImplementation("io.mockk:mockk:1.9")
}

allprojects {
    repositories {
        google()
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }

gradlePlugin {
    plugins {
        create("smartPropertiesPlugin") {
            id = "com.guidovezzoni.smartproperties"
            group = "com.guidovezzoni.smartproperties"
            version = "0.5.0-beta"
            @Suppress("UnstableApiUsage")
            displayName = "Smart Properties Plugin"
            @Suppress("UnstableApiUsage")
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

tasks.test {
    useJUnitPlatform()  // JUnit5

    testLogging {
        // if you want to log all events, use events = TestLogEvent.values().toSet()
        events = setOf(
            TestLogEvent.STARTED,
            TestLogEvent.PASSED,
            TestLogEvent.FAILED
        )
        // show standard out and standard error of the test
        // JVM(s) on the console
        showStandardStreams = true
    }
}
