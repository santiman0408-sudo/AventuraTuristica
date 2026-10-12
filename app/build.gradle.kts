plugins {
    alias(libs.plugins.android.application)

    // Administrar la clave de Google Maps
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.example.aventuraturistica"

    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.aventuraturistica"
        minSdk = 23
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    // Habilitar BuildConfig para la configuración de secretos
    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // Dependencias originales del proyecto
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)

    // Listado de reservas con RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.4.0")

    // Google Maps SDK para Android
    implementation("com.google.android.gms:play-services-maps:20.0.0")

    // Pruebas
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}

// Archivo que contiene la clave de Google Maps
secrets {
    propertiesFileName = "secrets.properties"
}



