package dependancies

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.materialDesign() {
    implementation("com.google.android.material:material:1.5.0")
}