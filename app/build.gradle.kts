
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // 放在最下面
    id("io.github.liuxb-tofu.v3-rotate-signer")
}

android {
    namespace = "com.blookliu.v3rotatesignerplugin"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.blookliu.v3rotatesignerplugin"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file("$projectDir/keystores/old-key.keystore")
            storePassword = "abcd1234"
            keyAlias = "old-key"
            keyPassword = "abcd1234"
            enableV1Signing = true
            enableV2Signing = true
            enableV3Signing = true
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.findByName("release")
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

V3SigningConfig {
    oldKeyStore.storeFile = file("$projectDir/keystores/old-key.keystore")
    oldKeyStore.storePassword = "abcd1234"

    newKeyStore.storeFile = file("$projectDir/keystores/new-key.keystore")
    newKeyStore.storePassword = "abcd1234"

    lineage = file("$projectDir/keystores/lineage")

    // 是否使用独立的gradle task，v3RotateSign${variantName}
    useSingleTask = false
}
