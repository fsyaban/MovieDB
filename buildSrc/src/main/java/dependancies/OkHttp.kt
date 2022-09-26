package dependancies

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.okHttp() {
    implementation("com.squareup.okhttp3:okhttp:+")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
}