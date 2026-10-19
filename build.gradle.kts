import java.lang.System.getenv
import java.lang.System.lineSeparator

plugins {
    alias(libs.plugins.kotlin)
    application
}

repositories {
    mavenCentral()
}

dependencies {
}

application {
    mainClass.set("me.madhead.github.actions.rjp.ReadJavaPropertiesKt")
}

java {
    toolchain {
        languageVersion.set(libs.versions.jvm.map(JavaLanguageVersion::of))
    }
}

tasks {
    register("javaVersion") {
        doLast {
            File(getenv("GITHUB_OUTPUT")).appendText("java-version=${libs.versions.jvm.get()}${lineSeparator()}")
        }
    }
}
