pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AutoMobile"
include(":shared")
include(":mechanicandroidapp")
include(":clientandroidapp")
include(":sharedAndroid")
