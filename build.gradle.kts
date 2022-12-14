import mappings.internal.Constants
import mappings.internal.FileConstants
import mappings.internal.tasks.build.CompressTinyTask
import mappings.internal.tasks.mappings.EnigmaMappingsTask

plugins {
    `maven-publish`
    `mappings-logic`
}

repositories {
    maven("https://maven.quiltmc.org/repository/release/")
    mavenCentral()
}

dependencies {
    val enigmaVersion: String by project
    enigmaRuntime("cuchaz:enigma-swing:${enigmaVersion}") {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "io.github.elierrr"
            artifactId = Constants.MAPPINGS_NAME
            version = Constants.MAPPINGS_VERSION

            val compressTiny: CompressTinyTask = tasks.compressTiny.get()

            artifact(compressTiny.compressedTiny) {
                classifier = "tiny"
                builtBy(compressTiny)
            }
            artifact(tasks.tinyJar.get())
        }
    }
    repositories {
        mavenLocal()
    }
}

tasks.create("build") {
    dependsOn("compressTiny", "tinyJar")
}

val mappings: Task by tasks.creating(EnigmaMappingsTask::class) {
    dependsOn(tasks.getByName("mergeJars"))
    jarToMap.set(FileConstants.INSTANCE.mergedJar)
}
