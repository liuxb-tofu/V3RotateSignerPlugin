## V3RotateSignerPlugin
*Read this in other languages: [English](README.md), [简体中文](README_zh.md).*

Android V3 signature itself supports old-key rotation, but the AGP plugin does not support this feature. Therefore, if you need to update your Android app signing key, you can use this plugin.

## How to use

```kotlin
plugins {
    // Put it at the bottom to make sure it executes last
    id("com.blookliu.v3-rotate-signer")
}

V3SigningConfig {
    oldKeyStore.storeFile = file("$projectDir/keystores/old-key.keystore")
    oldKeyStore.storePassword = "abcd1234"

    newKeyStore.storeFile = file("$projectDir/keystores/new-key.keystore")
    newKeyStore.storePassword = "abcd1234"

    lineage = file("$projectDir/keystores/lineage")
    // Whether to use an independent gradle task, v3RotateSign${variantName}
    useSingleTask = false
}
```