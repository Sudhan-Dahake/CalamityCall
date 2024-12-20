plugins {
    id("com.android.application") version "8.7.2" apply true
    id("com.google.gms.google-services") version "4.4.2" apply false
}

android {
    namespace = "com.example.calamitycall"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.calamitycall"
        minSdk = 29
        targetSdk = 33
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
    // Core libraries
    implementation("androidx.security:security-crypto:1.1.0-alpha03")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.9.2")

    // Jetpack Compose Material 3 components
    implementation("androidx.compose.material3:material3:1.3.1")

    // Networking libraries: Retrofit, Gson converter, and OkHttp logging
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    // Local Java libraries
    implementation(files("libs/javax.ws.rs-api-2.1.1.jar"))
    implementation("androidx.test.ext:junit:1.2.1")

    // Unit testing dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")

    // Mockito for mocking
    testImplementation("org.mockito:mockito-core:4.5.1")
    androidTestImplementation("org.mockito:mockito-android:4.5.1")

    // UI testing with Espresso
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.2.0")

    // Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))

    implementation("com.google.firebase:firebase-analytics")


    // Firebase cloud messaging
    implementation("com.google.firebase:firebase-messaging")
}
