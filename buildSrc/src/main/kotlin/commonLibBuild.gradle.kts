import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {

    defaultConfig {
        minSdk = BuildParams.MIN_SDK
        compileSdk = BuildParams.COMPILE_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_21.toString()
            languageVersion = "2.1"
        }
    }

    val localPropertiesFile = rootProject.file("local.properties")
    val properties = Properties()
    properties.load(FileInputStream(localPropertiesFile))

    buildTypes {
        release {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro", "proguard-gson.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_21.toString()
    }

    buildFeatures {
        buildConfig = true
    }
}