package dependancies

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.testUnit (){
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}