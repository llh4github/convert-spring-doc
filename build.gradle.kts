plugins {
    kotlin("jvm") version "1.9.21"
}

group = "io.github.llh4github"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.3.0")
    // SLF4J API
//    implementation("org.slf4j:slf4j-api:2.0.9")
    // Logback, a SLF4J implementation
    implementation("org.apache.logging.log4j:log4j-core:2.22.0")
    implementation("org.apache.logging.log4j:log4j-api:2.22.0")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.22.0")
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