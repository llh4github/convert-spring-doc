import org.gradle.jvm.tasks.Jar

group = "io.github.llh4github"
version = property("version")!!

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.21"
    id("org.graalvm.buildtools.native") version "0.9.28"
    application
}
repositories {
    mavenCentral()
    gradlePluginPortal()
}
dependencies {
    implementation(project(":core"))
    implementation(libs.clikt)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainClass.set("io.github.llh4github.sw3convert.cli.MainKt")
}
tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = application.mainClass
    }
}
graalvmNative {
    toolchainDetection.set(true)
    binaries {
        named("main") {
            imageName.set(application.applicationName)
            mainClass.set(application.mainClass)
            buildArgs.add("-O4")
        }
        named("test") {
            buildArgs.add("-O0")
        }
    }
    binaries.all {
        buildArgs.add("--verbose")
    }
}