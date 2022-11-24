buildscript {
    val compose_version by extra("1.3.1")
    val hilt_version by extra("2.44")
    val nav_version by extra("2.5.1")
    val ktor_version by extra("2.1.3")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        val kotlin_version = "1.7.10"
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hilt_version")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("com.android.tools.build:gradle:7.3.0")
        classpath ("org.jetbrains.kotlin:kotlin-serialization:$kotlin_version")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}