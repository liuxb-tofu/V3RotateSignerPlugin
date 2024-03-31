plugins {
    id("java-gradle-plugin")
    id("org.jetbrains.kotlin.jvm") version "1.9.10"
    id("com.gradle.plugin-publish") version "1.2.1"
}

group = "com.blookliu"
version = "1.0.0"

dependencies {
    implementation(gradleApi())
    implementation("com.android.tools.build:gradle:8.2.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.10")
}

gradlePlugin {
    website = "https://github.com/liuxb-tofu/V3RotateSignerPlugin"
    vcsUrl = "https://github.com/liuxb-tofu/V3RotateSignerPlugin.git"
    plugins {
        create("V3RotateSignerPlugin") {
            id = "com.blookliu.v3-rotate-signer"
            displayName = "Plugin for replace signing key"
            description = "A plugin for using v3 rotation signature to replace the secret key"
            tags = listOf("android", "signature", "build")
            implementationClass = "com.blookliu.v3_rotate_signer.SignerPlugin"
        }
    }
}

publishing{
    repositories {
        maven {
            name = "localPluginRepository"
            url = uri("../local-plugin-repository")
        }
    }
}

tasks.withType(Javadoc::class.java).all { enabled = false }