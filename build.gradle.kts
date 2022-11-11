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
            println("::set-output name=java-version::${libs.versions.jvm.get()}")
        }
    }
}
