plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    kotlin("plugin.serialization")
}



kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../clientIosApp/Podfile")
        framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                //KTOR
                implementation("io.ktor:ktor-client-core:${rootProject.extra["ktor_version"]}")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                implementation("io.ktor:ktor-client-auth:${rootProject.extra["ktor_version"]}")
                api("io.ktor:ktor-client-mock:${rootProject.extra["ktor_version"]}")
                implementation("io.ktor:ktor-client-content-negotiation:${rootProject.extra["ktor_version"]}")
                implementation("io.ktor:ktor-serialization-kotlinx-json:${rootProject.extra["ktor_version"]}")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting{
            dependencies {
                api("io.ktor:ktor-client-cio:${rootProject.extra["ktor_version"]}")
                api("io.ktor:ktor-client-android:${rootProject.extra["ktor_version"]}")

            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                implementation("io.ktor:ktor-client-ios:${rootProject.extra["ktor_version"]}")
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.example.automobile"
    compileSdk = 33
    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }
}