package dependancies

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.gander() {
    implementation("com.ashokvarma.android:gander-imdb:3.1.0")
}