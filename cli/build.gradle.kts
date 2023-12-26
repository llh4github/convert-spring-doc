import org.gradle.jvm.tasks.Jar

group = "io.github.llh4github"
version = property("version")!!

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.21"
    java
    application
}
repositories {
    mavenCentral()
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

