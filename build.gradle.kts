// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath(GradleLibraries.GRADLE)
        classpath(GradleLibraries.KOTLIN_GRADLE_PLUGIN)
        classpath(GradleLibraries.NAVIGATION_SAFE_ARGS_GRADLE_PLUGIN)
        classpath(GradleLibraries.HILT_ANDROID_GRADLE_PLUGIN)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}