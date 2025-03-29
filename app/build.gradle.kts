plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kover)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.kotlin.serialization)
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

    signingConfigs {
        getByName("debug")
        create("release") {
            storeFile = file("signing/marsKeystore")
            storePassword = System.getenv("MARS_ANDROID_KEYSTORE_PASSWORD")
            keyAlias = System.getenv("MARS_ANDROID_KEYSTORE_ALIAS")
            keyPassword = System.getenv("MARS_ANDROID_KEYSTORE_PRIVATE_KEY_PASSWORD")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            signingConfig = signingConfigs.getByName("debug")
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
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

kover {
    reports {
        filters {
            excludes {
                annotatedBy("*Composable")
                classes("*.ui.*")
                classes("*.di.*")
                classes("*Activity")
                classes("*generated*")
                classes("*ComposableSingletons*")
            }
        }
        verify {
            rule("Minimum coverage") {
                bound {
                    minValue = 80
                }
            }
        }
        variant("debug") {
            xml {
                onCheck = true
            }
            html {
                onCheck = true
            }
        }
    }
}

dependencies {
    implementation(libs.bundles.layer.presentation)
    implementation(platform(libs.androidx.compose.bom))
    ksp(libs.bundles.compilers.ksp.generic)

    testImplementation(libs.bundles.testing.unit)

    //Modules implementations
    implementation(project(":common:di"))
    implementation(project(":designsystem"))
    implementation(project(":presentation"))

    // Impl for kover merging reports on this root module
    kover(project(":domain"))
    kover(project(":data"))
}