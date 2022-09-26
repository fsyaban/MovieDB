package dependancies

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.retrofit() {
    implementation("com.squareup.retrofit2:retrofit:+")
    implementation("com.squareup.retrofit2:converter-gson:+")
}