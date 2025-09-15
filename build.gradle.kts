// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
}

subprojects {
    configurations.all {
        exclude(group = "androidx.annotation", module = "annotation")
        exclude(group = "androidx.arch.core", module = "core-runtime")
        exclude(group = "androidx.concurrent", module = "concurrent-futures")
        resolutionStrategy {
            force("androidx.annotation:annotation:1.9.1")
            force("androidx.arch.core:core-runtime:2.2.0")
            force("androidx.concurrent:concurrent-futures:1.3.0")
        }
    }
}
