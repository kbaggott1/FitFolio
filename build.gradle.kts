// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.6.0"
}

// Apply klint to our code
/*
ktlint {
    verbose.set(true)
    outputToConsole.set(true)
    coloredOutput.set(true)
    reporters {
        reporter(ReporterType.CHECKSTYLE)
        reporter(ReporterType.JSON)
        reporter(ReporterType.HTML)
    }
    filter {
        exclude("**/style-violations.kt")
    }
}
*/

ktlint {
    version.set("11.16.0") // Specify the desired ktlint version
    reporters = listOf("plain", "checkstyle") // Use plain and checkstyle reporters
    verbose.set(true) // Show verbose output
}

tasks.register("ktlintStagingDebugCheck", KtlintCheckTask::class) {
    description = "Runs ktlint on all Kotlin sources (StagingDebug)"
    group = "verification"
    sourceSets = listOf(sourceSets["stagingDebug"]) // Replace with your desired source set
}

tasks.named("check") {
    dependsOn("ktlintStagingDebugCheck")
}
