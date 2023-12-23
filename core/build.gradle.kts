group = "io.github.llh4github"
version = property("version")!!
plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.21"
    `java-library`
}

dependencies {
    implementation(libs.log4j.core)
    implementation(libs.log4j.api)
    implementation(libs.log4j.slf4j.impl)
    implementation(libs.log4j.api.kotlin)

    implementation(libs.javaparser)

    implementation(libs.swagger)
    implementation(libs.swagger3)

    testImplementation(kotlin("test"))
    testImplementation(libs.junit.jupiter.params)

}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

