package io.github.blookliu.v3_rotate_signer

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.Project
import java.io.File

object SignHelper {
    fun doSign(
        variant: ApplicationVariant,
        project: Project
    ) {
        project.logger.info("do v3 signing")
        val appExtension = project.extensions.findByName("android") as AppExtension
        val v3SigningConfig =
            project.extensions.findByName("V3SigningConfig") as V3SigningConfig
        val separator = File.separator
        val packageTask = variant.packageApplicationProvider.get()
        val apkFiles =
            packageTask.outputs.files.asFileTree.files.filter { file -> file.isFile && file.extension == "apk" }
        val buildToolDir =
            "${appExtension.sdkDirectory.absolutePath}${separator}build-tools${separator}${appExtension.buildToolsVersion}${separator}"
        val apkSignerExe =
            if (isWindows()) "${buildToolDir}apksigner.bat" else "${buildToolDir}apksigner"
        apkFiles.forEach { apkFile ->
            val v3File =
                "${apkFile.parentFile.absolutePath}${separator}${apkFile.name}.v3"
            val ret = project.exec { execSpec ->
                execSpec.executable = apkSignerExe
                execSpec.args("sign")
                execSpec.args("--ks")
                execSpec.args(v3SigningConfig.oldKeyStore?.storeFile?.absolutePath)
                execSpec.args("--ks-pass")
                execSpec.args("pass:${v3SigningConfig.oldKeyStore?.storePassword}")
                execSpec.args("--next-signer")
                execSpec.args("--ks")
                execSpec.args(v3SigningConfig.newKeyStore?.storeFile?.absolutePath)
                execSpec.args("--ks-pass")
                execSpec.args("pass:${v3SigningConfig.newKeyStore?.storePassword}")
                execSpec.args("--lineage")
                execSpec.args(v3SigningConfig.lineage?.absolutePath)
                execSpec.args("--out")
                execSpec.args(v3File)
                execSpec.args(apkFile.absolutePath)
            }
            if (ret.exitValue == 0) {
                apkFile.delete()
                File(v3File).renameTo(apkFile)
                project.logger.info("v3 sign success")
            } else {
                project.logger.error("v3 signer failed: ${ret.exitValue}")
            }
        }
    }

    private fun isWindows(): Boolean {
        return System.getProperty("os.name", "").toLowerCase().startsWith("windows")
    }
}