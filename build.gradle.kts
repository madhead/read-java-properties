import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm").version(Versions.Plugins.KOTLIN)
    application
}

repositories {
    mavenCentral()
}

dependencies {
}

application {
    mainClass.set("me.madhead.github.actions.rjp.ReadJavaPropertyKt")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(Versions.JVM))
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "${Versions.JVM}"
            useIR = true
        }
    }

    register("javaVersion") {
        doLast {
            println("::set-output name=java-version::${Versions.JVM}")
        }
    }
}
