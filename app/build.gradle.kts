import java.util.Properties

plugins {
    id("com.android.application")
}

android {
    namespace = "app.akexorcist.checkscreen"
    compileSdk = 37

    defaultConfig {
        applicationId = "app.akexorcist.checkscreen"
        minSdk = 21
        targetSdk = 37
        versionCode = (project.findProperty("releaseVersionCode") as String?)?.toInt() ?: 217
        versionName = project.findProperty("releaseVersionName") as String? ?: "2.4.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val properties = Properties()
    if (File("local.properties").exists()) {
        properties.load(File("local.properties").inputStream())
    }

    signingConfigs {
        create("release") {
            storeFile = properties.getProperty("keystore_path")?.let { file(it) }
            storePassword = properties.getProperty("keystore_password")
            keyAlias = properties.getProperty("keystore_key_alias")
            keyPassword = properties.getProperty("keystore_key_password")

            enableV3Signing = true
            enableV4Signing = true
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
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
    implementation(project(path = ":screenChecker"))
    implementation("com.google.android.gms:play-services-instantapps:18.2.0")
}
