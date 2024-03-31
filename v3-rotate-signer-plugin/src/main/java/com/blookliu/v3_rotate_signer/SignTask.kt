package com.blookliu.v3_rotate_signer

import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.configurationcache.extensions.capitalized

open class SignTask : DefaultTask() {
    private lateinit var variant: ApplicationVariant

    @TaskAction
    fun run() {
        SignHelper.doSign(variant, project)
    }

    class CreateAction(private val variant: ApplicationVariant) : Action<SignTask> {

        val name = "v3RotateSign${variant.name.capitalized()}"
        val type = SignTask::class.java

        override fun execute(task: SignTask) {
            task.variant = variant
            task.dependsOn("assemble${variant.name.capitalized()}")
        }

    }
}