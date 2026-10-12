buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        // Complemento para administrar la clave de Google Maps
        classpath(
            "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1"
        )
    }
}

plugins {
    // Conserva el complemento Android del proyecto
    alias(libs.plugins.android.application) apply false
}

