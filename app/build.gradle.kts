import java.io.FileInputStream
import java.util.Properties

plugins {
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.diphrogram.kolsa_test"
    compileSdk = BuildParams.COMPILE_SDK

    val localPropertiesFile = rootProject.file("local.properties")
    val properties = Properties()
    properties.load(FileInputStream(localPropertiesFile))

    defaultConfig {
        applicationId = "com.diphrogram.kolsa_test"
        minSdk = BuildParams.MIN_SDK
        targetSdk = BuildParams.TARGET_SDK
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file("release_key.jks")
            storePassword = properties.getProperty("RELEASE_STORE_PASSWORD")
            keyAlias = properties.getProperty("RELEASE_ALIAS")
            keyPassword = properties.getProperty("RELEASE_KEY_PASSWORD")
        }
        getByName("debug") {
            storeFile = file("debug_key.jks")
            storePassword = properties.getProperty("DEBUG_STORE_PASSWORD")
            keyAlias = properties.getProperty("DEBUG_ALIAS")
            keyPassword = properties.getProperty("DEBUG_KEY_PASSWORD")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
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
        viewBinding = true
    }
}

dependencies {

    implementation(project(Modules.UTILS))
    implementation(project(Modules.DOMAIN))
    implementation(project(Modules.DATA))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)

    // DI
    implementation(libs.hilt)
    implementation(libs.hilt.core)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)


    // Network
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // ViewModelScope
    implementation(libs.androidx.lifecycle.common)
    implementation(libs.androidx.lifecycle.viewmodel)

    // ExoPlayer
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.session)
}