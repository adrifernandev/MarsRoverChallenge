plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "${Versions.APPLICATION_ID}.common.di"
    compileSdk = Versions.COMPILE_SDK

    defaultConfig {
        minSdk = Versions.MIN_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    //
    // [Feature-Manager Common di dependencies]
    //
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":presentation"))
}