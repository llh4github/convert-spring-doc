group = "io.github.llh4github"
version = property("version")!!
plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.21"
    id("org.graalvm.buildtools.native") version "0.9.28"
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
}
repositories {
    mavenCentral()
    gradlePluginPortal()
}
val fxVersion = "17"
dependencies {
    implementation("org.openjfx:javafx-controls:${fxVersion}")
    implementation("org.openjfx:javafx-fxml:${fxVersion}")
}
javafx {
    version = "17"
    modules("javafx.controls", "javafx.fxml")
}
application{
    mainClass="io.github.llh4github.sw3convert.gui.MainKt"
}

