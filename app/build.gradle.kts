plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.aplicaciondos" // 🔹 Aquí va tu namespace
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.aplicaciondos"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.23")

    // Compose
    implementation("androidx.compose.ui:ui:1.5.3")
    implementation("androidx.compose.material3:material3:1.2.0-alpha06")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.3")
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.cardview)
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.3")

    // Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.7.3")

    // Activity Compose
    implementation("androidx.activity:activity-compose:1.9.0")

    // RecyclerView (si lo necesitas)
    implementation("androidx.recyclerview:recyclerview:1.4.0")

    // Coil (opcional para imágenes)
    implementation("io.coil-kt:coil-compose:2.4.0")
}
