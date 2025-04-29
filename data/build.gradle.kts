import java.io.FileInputStream
import java.util.Properties

plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.ksp.version.get().pluginId)
    id("commonLibBuild")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.diphrogram.data"

    val localPropertiesFile = rootProject.file("local.properties")
    val properties = Properties()
    properties.load(FileInputStream(localPropertiesFile))

    buildTypes {
        release {
            buildConfigField("String", "BASE_URL", "\"${properties.getProperty("BASE_URL")}\"")
        }
        debug {
            buildConfigField("String", "BASE_URL", "\"${properties.getProperty("BASE_URL")}\"")
        }
    }
}

dependencies {

    implementation(project(Modules.UTILS))
    implementation(project(Modules.DOMAIN))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Network
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // DI
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
}