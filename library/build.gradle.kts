import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

group = "dev.anthonyhfm"
version = "0.1.1"

kotlin {
    jvm()
    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(libs.webkit)
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.ui)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                implementation(libs.kotlinx.coroutines.swing)
                implementation(libs.kcef)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

android {
    namespace = "dev.anthonyhfm.kowe"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates(group.toString(), "kowe", version.toString())

    pom {
        name = "Kowe"
        description = "Compose Webviews for Kotlin Multiplatform"
        inceptionYear = "2024"
        url = "https://github.com/anthonyhfm/kowe/"

        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "anthonyhfm"
                name = "Anthony Hofmeister"
                url = "https://github.com/anthonyhfm"
            }
        }
        scm {
            url = "https://github.com/anthonyhfm/kowe"
            connection = "scm:git:git://github.com/anthonyhfm/kowe.git"
            developerConnection = "scm:git:ssh://git@github.com/anthonyhfm/kowe.git"
        }
    }
}
