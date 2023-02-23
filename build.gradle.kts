import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.totalled"
version = "1.0-SNAPSHOT"

val lwjglVersion = "3.3.1"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

@OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(compose.material3)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.6.4")
                listOf("lwjgl", "lwjgl-nfd").forEach { lwjglDep ->
                    implementation("org.lwjgl:$lwjglDep:$lwjglVersion")
                    listOf(
                        "natives-windows",
                        "natives-windows-x86",
                        "natives-windows-arm64",
                        "natives-macos",
                        "natives-macos-arm64",
                        "natives-linux",
                        "natives-linux-arm64",
                        "natives-linux-arm32",
                    ).forEach { native ->
                        runtimeOnly("org.lwjgl:$lwjglDep:$lwjglVersion:$native")
                    }
                }
                implementation("cafe.adriel.voyager:voyager-navigator-desktop:1.0.0-rc03")
                implementation("org.apache.poi:poi:5.2.3")
                implementation("org.apache.poi:poi-ooxml:5.2.3")
                implementation("org.apache.logging.log4j:log4j-core:2.20.0")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "totalled"
            packageVersion = "1.0.0"
        }
    }
}
