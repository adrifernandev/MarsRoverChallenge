// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kover) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.com.google.devtools.ksp) apply false
    alias(libs.plugins.com.google.dagger.hilt.android) apply false
    alias(libs.plugins.ktlint)
}

tasks.register<Copy>("copyPreCommitHook") {
    description = "Copy pre-commit git hook from the scripts to the .git/hooks folder."
    group = "git hooks"
    outputs.upToDateWhen { false }
    from("$rootDir/hooks/pre-commit")
    into("$rootDir/.git/hooks/")
}

val isWindows = org.gradle.internal.os.OperatingSystem.current().isWindows

tasks.register("setExecutableGitHooks", Exec::class.java) {
    description = "Sets executable permission on git hooks."
    group = "git hooks"
    if (isWindows) {
        commandLine("cmd", "/c", "attrib", "+x", "$rootDir\\.git\\hooks\\*")
    } else {
        commandLine("chmod", "-R", "+x", "$rootDir/.git/hooks/")
    }
    dependsOn("copyPreCommitHook")
}

tasks.register("installGitHooks") {
    description = "Installs the pre-commit git hooks from /git-hooks."
    group = "git hooks"
    dependsOn("copyPreCommitHook", "setExecutableGitHooks")
    doLast {
        logger.info("Git hooks installed successfully.")
    }
}

afterEvaluate {
    tasks.getByPath(":app:preBuild").dependsOn(":installGitHooks")
}
