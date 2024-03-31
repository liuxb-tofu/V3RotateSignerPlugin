package io.github.blookliu.v3_rotate_signer

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.Plugin
import org.gradle.api.Project

class SignerPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.create("V3SigningConfig", V3SigningConfig::class.java)

        project.afterEvaluate {
            project.logger.info("evaluate v3 signing")
            val appExtension = it.extensions.findByName("android") as AppExtension
            val v3SigningConfig =
                project.extensions.findByName("V3SigningConfig") as V3SigningConfig
            if (v3SigningConfig.useSingleTask) {
                appExtension.applicationVariants.all {
                    createTask(project, it)
                }
            } else {
                appExtension.applicationVariants.all {variant ->
                    val packageTask = variant.packageApplicationProvider.get()
                    packageTask.doLast {
                        SignHelper.doSign(variant, project)
                    }
                }
            }
        }
    }

    private fun createTask(project: Project, variant: ApplicationVariant) {
        val action = SignTask.CreateAction(variant)
        if (project.tasks.findByName(action.name) == null) {
            println("create task ${action.name}")
            project.tasks.register(action.name, action.type, action)
        }
    }

}