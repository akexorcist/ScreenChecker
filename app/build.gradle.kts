import java.util.Properties

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.akexorcist.checkscreen"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.akexorcist.checkscreen"
        minSdk = 16
        targetSdk = 34
        versionCode = 212
        versionName = "2.2.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val properties = Properties()
    if (File("local.properties").exists()) {
        properties.load(File("local.properties").inputStream())
    }

    signingConfigs {
        create("release") {
            storeFile = file(properties.getProperty("keystore_path"))
            storePassword = properties.getProperty("keystore_password")
            keyAlias = properties.getProperty("keystore_key_alias")
            keyPassword = properties.getProperty("keystore_key_password")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    implementation(project(path = ":screenChecker"))
    implementation("com.google.android.gms:play-services-instantapps:17.0.0")
}
