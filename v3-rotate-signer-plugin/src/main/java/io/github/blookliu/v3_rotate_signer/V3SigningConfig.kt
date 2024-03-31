package io.github.blookliu.v3_rotate_signer

import groovy.lang.Closure
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.util.ConfigureUtil
import java.io.File

open class V3SigningConfig {
    var oldKeyStore = SignerConfig()
    var newKeyStore = SignerConfig()
    var lineage: File? = null
    var useSingleTask: Boolean = false

    fun oldKeyStore(c : Closure<SignerConfig>) {
        ConfigureUtil.configure(c, oldKeyStore)
    }

    fun newKeyStore(c : Closure<SignerConfig>) {
        ConfigureUtil.configure(c, newKeyStore)
    }

    fun lineage(file: File) {
        lineage = file
    }

    fun useSingleTask(singleTask: Boolean) {
        useSingleTask = singleTask
    }
}

open class SignerConfig {
    var storeFile: File? = null
    var storePassword: String? = null

    fun storeFile(file: File) {
        storeFile = file
    }

    fun storePassword(password: String) {
        storePassword = password
    }
}
