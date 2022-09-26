package dependancies

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.room(){
    implementation ("androidx.room:room-runtime:2.3.0")
    kapt ("androidx.room:room-compiler:2.3.0")
}