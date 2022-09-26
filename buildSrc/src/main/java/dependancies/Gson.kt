package dependancies

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.gson() {
    implementation("com.google.code.gson:gson:+")
}