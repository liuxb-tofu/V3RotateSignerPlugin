# V3RotateSignerPlugin

android v3签名本身是支持新旧秘钥轮替的，但是AGP插件并没有支持该能力，所以如果你的Android应用需要更新签名秘钥，可使用本插件。

## 使用方法

```kotlin
plugins {
    // 放在最下面,保证最后执行的
    id("com.blookliu.v3-rotate-signer")
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
```

