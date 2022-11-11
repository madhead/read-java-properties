import java.lang.System.getenv
import java.lang.System.lineSeparator
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = libs.versions.jvm.get()
        }
    }

    register("javaVersion") {
        doLast {
            File(getenv("GITHUB_OUTPUT")).appendText("java-version=${libs.versions.jvm.get()}${lineSeparator()}")
        }
    }
}
