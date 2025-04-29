import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

gradlePlugin {
    plugins {
        register("common-lib-build") {
            id = "common-lib-build"
            implementationClass = "commonLibBuild"
        }
    }
}

repositories {
    google()
    mavenCentral()
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = JavaVersion.VERSION_21.toString()
}

dependencies {

    implementation(libs.gradle)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.javapoet)
}