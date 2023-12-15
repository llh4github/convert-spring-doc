plugins {
    kotlin("jvm") version "1.9.21"
}

group = "io.github.llh4github"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

//    runtimeOnly("com.github.ajalt.clikt:clikt-jvm:4.2.1")
//    runtimeOnly("com.github.ajalt:clikt:4.2.1")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.19")
    implementation("io.swagger:swagger-annotations:1.6.12")
//    implementation("org.springdoc:springdoc-openapi-common:1.6.8")
    implementation("com.github.javaparser:javaparser-core:3.25.7")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}