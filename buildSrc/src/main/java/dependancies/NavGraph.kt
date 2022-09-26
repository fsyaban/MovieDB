package dependancies

import org.gradle.api.artifacts.dsl.DependencyHandler



fun DependencyHandler.NavGraph(){
    val nav_version = "2.4.2"

    implementation ("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation ("androidx.navigation:navigation-ui-ktx:$nav_version")
}