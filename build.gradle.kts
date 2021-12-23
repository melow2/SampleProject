// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        jcenter()
    }
    dependencies {
        classpath(Deps.android_plugin)
        classpath(Deps.Kotlin.gradle_plugin)
        classpath(Deps.Hilt.gradlePlugin)
        classpath(Deps.AndroidX.Navigation.plugin)
        classpath("com.android.tools.build:gradle:7.0.4")
    }
}

allprojects {

    configurations.all {
        resolutionStrategy.eachDependency {
            when {
                requested.name.startsWith("kotlin-stdlib") -> {
                    useTarget(
                        "${requested.group}:${requested.name.replace(
                            "jre",
                            "jdk"
                        )}:${requested.version}"
                    )
                }
                else -> when (requested.group) {
                    "org.jetbrains.kotlin" -> useVersion(Deps.Versions.kotlin)
                }
            }
        }
    }

    repositories {
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
        maven(url = "https://jitpack.io")
    }
}


//tasks.register("clean").configure {
//    delete("build")
//}
tasks.register("clean", Delete::class) { delete(rootProject.buildDir) }