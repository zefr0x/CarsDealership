import org.gradle.plugins.ide.eclipse.model.Classpath
import org.gradle.plugins.ide.eclipse.model.SourceFolder

layout.buildDirectory = file("build/gradle")

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    java
    application
    eclipse
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // This dependency is used by the application.
    implementation("org.mindrot:jbcrypt:0.4")
    implementation("com.formdev:flatlaf:3.4")
    implementation("com.formdev:flatlaf-intellij-themes:3.4")
    implementation ("org.xerial:sqlite-jdbc:3.44.0.0")
    implementation ("org.apache.logging.log4j:log4j-api:2.23.1")
    implementation ("org.apache.logging.log4j:log4j-core:2.23.1")
    implementation ("org.slf4j:slf4j-log4j12:2.0.13")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    // Define the main class for the application.
    mainClass.set("carsdealership.App")
}

eclipse {
    classpath {
        defaultOutputDir = file("build/eclipse")
        file {
            whenMerged(
                    Action<Classpath> { ->
                        entries.filter { it.kind == "src" }.forEach {
                            if (it is SourceFolder) {
                                it.output = it.output.replace("bin/", "build/eclipse/")
                            }
                        }
                    }
            )
        }
    }
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

tasks.shadowJar {
   archiveBaseName.set("CarsDealership")
   archiveVersion.set("0.1.0")
   archiveClassifier.set("")
}
