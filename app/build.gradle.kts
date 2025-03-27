plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = Versions.APPLICATION_ID
    compileSdk = Versions.COMPILE_SDK

    defaultConfig {
        applicationId = Versions.APPLICATION_ID
        minSdk = Versions.MIN_SDK
        targetSdk = Versions.COMPILE_SDK
        versionCode = Versions.APP_VERSION_CODE
        versionName = Versions.APP_VERSION_NAME

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
    compileOptions {
        sourceCompatibility = Versions.javaVersion
        targetCompatibility = Versions.javaVersion
    }
    kotlinOptions {
        jvmTarget = Versions.JVM_TARGET
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.bundles.layer.presentation)
    implementation(platform(libs.androidx.compose.bom))

    implementation(project(":designsystem"))
}