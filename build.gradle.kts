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

//    "functionalTestImplementation"("org.junit.jupiter:junit-jupiter:5.6.2")

    testImplementation(gradleTestKit())
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
            version = "0.5.1-beta"
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

/*
// Add a source set for the functional test suite
// https://github.com/gradle/gradle/blob/master/subprojects/docs/src/samples/gradle-plugin/kotlin/build.gradle.kts
val functionalTestSourceSet = sourceSets.create("functionalTest") {
//    compileClasspath += sourceSets.main.output + configurations.testCompile
//    runtimeClasspath += output + compileClasspath + configurations.testRuntime
    compileClasspath += sourceSets.main.get().output
    runtimeClasspath += sourceSets.main.get().output

}

gradlePlugin.testSourceSets(functionalTestSourceSet)

// Add a task to run the functional tests
val functionalTest by tasks.creating(Test::class) {
    testClassesDirs = functionalTestSourceSet.output.classesDirs
    classpath = functionalTestSourceSet.runtimeClasspath
}

val check by tasks.getting(Task::class) {
    // Run the functional tests as part of `check`
    dependsOn(functionalTest)
}

gradlePlugin {
    testSourceSets(sourceSets["functionalTest"])
//    testSourceSets(functionalTestSourceSet)
}

dependencies {
    "functionalTestImplementation"("org.junit.jupiter:junit-jupiter:5.6.2")
    "functionalTestImplementation"("io.mockk:mockk:1.9")
}
*/



// Write the plugin's classpath to a file to share with the tests
// see https://docs.gradle.org/current/userguide/test_kit.html#sec:working_with_gradle_versions_prior_to_28
tasks.register("createClasspathManifest") {
    val outputDir = file("$buildDir/$name")

    inputs.files(sourceSets.main.get().runtimeClasspath)
        .withPropertyName("runtimeClasspath")
        .withNormalizer(ClasspathNormalizer::class)
    outputs.dir(outputDir)
        .withPropertyName("outputDir")

    doLast {
        outputDir.mkdirs()
        file("$outputDir/plugin-classpath.txt")
            .writeText(sourceSets.main.get().runtimeClasspath.joinToString("\n"))
    }
}

// Add the classpath file to the test runtime classpath
dependencies {
    testRuntimeOnly(files(tasks["createClasspathManifest"]))
}

