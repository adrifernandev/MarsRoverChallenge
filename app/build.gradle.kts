plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kover)
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

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {

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

    testImplementation(libs.bundles.testing.unit)

    //Modules implementations
    implementation(project(":designsystem"))
    implementation(project(":presentation"))
}