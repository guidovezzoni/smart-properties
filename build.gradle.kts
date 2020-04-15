import org.gradle.api.tasks.testing.logging.TestLogEvent

repositories {
    mavenCentral()
    jcenter()
}

plugins {
    kotlin("jvm") version "1.3.70"
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "0.11.0"
    `maven-publish`
}

dependencies {
    implementation("com.android.tools.build:gradle:3.6.1")

//    testCompile("junit", "junit", "4.12")
//    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
//    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.2.0")

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

gradlePlugin {
    plugins {
        create("smartPropertiesPlugin") {
            id = "com.guidovezzoni.smartproperties"
            group = "com.guidovezzoni.smartproperties"
            version = "0.3.0-beta"
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

// see https://levelup.gitconnected.com/on-the-mysteries-of-kotlin-test-444cf094e69f
tasks.test {
    // useJUnit() // JUnit4
    useJUnitPlatform()  // JUnit5
    // useTestNG() // TestNG

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
