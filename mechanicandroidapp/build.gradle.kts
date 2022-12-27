plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.mechanicandroidapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.mechanicandroidapp"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        //testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.example.mechanicandroidapp.di.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "${rootProject.extra["compose_version"]}"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":sharedAndroid"))

    //DATEPICKER
    implementation("io.github.vanpra.compose-material-dialogs:datetime:0.8.1-rc")

    //LOCALDATE.now() for API < 26
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.6")

    //TEST
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    androidTestImplementation("junit:junit:4.13.2")
    testImplementation("com.google.dagger:hilt-android-testing:${rootProject.extra["hilt_version"]}")
    kaptTest("com.google.dagger:hilt-android-compiler:${rootProject.extra["hilt_version"]}")
    androidTestImplementation("com.google.dagger:hilt-android-testing:${rootProject.extra["hilt_version"]}")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:${rootProject.extra["hilt_version"]}")
    testImplementation("org.junit.jupiter:junit-jupiter")
    androidTestImplementation("androidx.test:runner:1.5.1")

    //HILT
    implementation("com.google.dagger:hilt-android:${rootProject.extra["hilt_version"]}")
    kapt("com.google.dagger:hilt-android-compiler:${rootProject.extra["hilt_version"]}")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    //BASIC
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("androidx.compose.ui:ui:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.ui:ui-tooling-preview:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.material:material:${rootProject.extra["compose_version"]}")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["compose_version"]}")
    debugImplementation("androidx.compose.ui:ui-tooling:${rootProject.extra["compose_version"]}")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${rootProject.extra["compose_version"]}")
}
kapt {
    correctErrorTypes = true
}