import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint)
}

val properties =
    Properties().apply {
        load(project.rootProject.file("local.properties").inputStream())
    }

android {
    namespace = "com.example.eldercare"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.eldercare"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // TODO: 카카오 키 설정
        buildConfigField("String", "KAKAO_API_KEY", "\"temp_key\"")
        buildConfigField("String", "KAKAO_REDIRECT_URI", "\"temp_uri\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
    lint {
        baseline = file("lint-baseline.xml")
        abortOnError = false
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // DataStore
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.preferences.core)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    ksp(libs.hilt.android.compiler)

    // Timber
    implementation(libs.timber)

    // Glide
    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)

    // coil
    implementation(libs.coil)

    // ViewPager2
    implementation(libs.androidx.viewpager2)

    // Coil
    // implementation(libs.coil.compose)

    // navigation
    implementation(libs.bundles.navigation)

    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

    // Coroutines
    implementation(libs.kotlinx.coroutines)

    // Network
    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.retrofit)

    // splashscreen
    implementation(libs.core.splashscreen)

    // EncryptedSharedPreferences
    implementation(libs.androidx.security.crypto)

}
