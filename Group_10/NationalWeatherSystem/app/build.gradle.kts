plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.nationalweathersystem"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.nationalweathersystem"
        minSdk = 29
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
    buildFeatures {
        viewBinding = true
    }
    packaging {
        resources {
            excludes += "/META-INF/spring.tooling"
            excludes += "/META-INF/versions/9"
            excludes += "/META-INF/spring.handlers"
            excludes += "/META-INF/spring.schemas"
            excludes += "/META-INF/license.txt"
            excludes += "/META-INF/DEPENDENCIES"
            excludes += "/META-INF/notice.txt"
        }
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.appium.java.client) {
        exclude(group = "org.seleniumhq.selenium", module = "selenium-api")
        exclude(group = "commons-logging", module = "commons-logging")
    }
    androidTestImplementation(libs.androidx.test.uiautomator)


}