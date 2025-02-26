plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    id("kotlin-parcelize")

    alias(libs.plugins.kotlin.serialization)
}
android {
    namespace = "com.kurokawa"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.kurokawa"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


    }
    viewBinding {
        enable = true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}




dependencies {
    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Material Design
    implementation(libs.material)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.ksp)

    // Navigation
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // Glide
    implementation(libs.glide)
    ksp(libs.glide.ksp)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // Lifecycle
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.runtime)

    // Corrutinas
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // Hilt

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //javapoet
    implementation(libs.javapoet)

    //hhttp
    implementation(libs.okhttp.logging.interceptor)

    //Serialize
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)

    //Koin similar a Dagger
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.navigation)


    //Paper DB
    implementation(libs.paperdb)


}