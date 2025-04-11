plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // Configuración de los plugins para el proyecto
    id("com.android.application") version "8.0.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
    id("org.jetbrains.kotlin.compose") version "1.4.0" apply false
}

android {
    namespace = "com.example.celestica"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.celestica"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildscript {
        repositories {
            google()
            mavenCentral()
        }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation (libs.kotlin.stdlib)
    implementation (libs.androidx.lifecycle.runtime.ktx)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)



    //open cv
    implementation (libs.opencv.android)
    implementation (libs.ui) // O la última versión de Compose
    implementation (libs.material3) // Si usas Material3
    implementation (libs.androidx.activity.compose.v160)
    implementation (libs.ui.tooling.preview)
    implementation (libs.androidx.lifecycle.runtime.ktx.v251)

    // Jetpack Compose
    implementation (libs.androidx.ui.v130)
    implementation (libs.androidx.material3.v110)
    implementation (libs.androidx.ui.tooling.preview.v130)

    // Navigation Compose
    implementation(libs.androidx.navigation.compose)



}