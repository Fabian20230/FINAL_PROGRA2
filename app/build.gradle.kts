plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "gt.edu.umg.final_progra2"
    compileSdk = 34

    defaultConfig {
        applicationId = "gt.edu.umg.final_progra2"
        minSdk = 27
        targetSdk = 34
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("com.google.android.material:material:1.9.0")
// Google Play Services: Location (para obtener la ubicación del usuario)
    implementation("com.google.android.gms:play-services-location:21.0.1")

// CameraX (para captura de fotos)
    implementation("androidx.camera:camera-core:1.1.0")
    implementation("androidx.camera:camera-camera2:1.1.0")
    implementation("androidx.camera:camera-lifecycle:1.1.0")
    implementation("androidx.camera:camera-view:1.0.0-alpha32")

// Google Maps (opcional, para mostrar mapas y rutas en el historial)
    implementation("com.google.android.gms:play-services-maps:18.1.0")

// Glide (para manejar y mostrar imágenes en la aplicación)
    implementation("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")

    implementation ("androidx.cardview:cardview:1.0.0")
}