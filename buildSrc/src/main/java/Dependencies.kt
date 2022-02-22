object Versions {
    // Android SDK project versions
    const val COMPILE_SDK = 31
    const val MIN_SDK = 23
    const val TARGET_SDK = 31

    // Library versions
    const val GRADLE = "7.0.4"
    const val NAVIGATION_SAFE_ARGS_GRADLE_PLUGIN = "2.3.5"
    const val KOTLIN = "1.6.10"
    const val ROOM = "2.3.0"
    const val HILT = "2.38.1"
    const val CORE_KTX = "1.7.0"
    const val APPCOMPAT = "1.4.0"
    const val MATERIAL = "1.4.0"
    const val JUNIT = "4.13.2"
    const val TEST_JUNIT = "1.1.3"
    const val ESPRESSO_CORE = "3.4.0"
    const val KOTLINX_COROUTINES_ANDROID = "1.5.1"
    const val CONSTRAINTLAYOUT = "2.1.2"
    const val NAVIGATION_FRAGMENT_KTX = "2.3.5"
    const val NAVIGATION_UI_KTX = "2.3.5"
    const val LIFECYCLE_LIVEDATA_KTX = "2.4.0"
    const val LIFECYCLE_VIEWMODEL_KTX = "2.4.0"
    const val LEGACY_SUPPORT_V4 = "1.0.0"
    const val DATABINDING_COMPILER = "3.1.4"
    const val GSON = "2.8.7"
    const val RETROFIT = "2.9.0"
    const val RETROFIT_CONVERTER_GSON = "2.9.0"
    const val LOGGING_INTERCEPTOR = "4.9.0"
}

object GradlePlugins {
    const val APPLICATION = "com.android.application"
    const val LIBRARY = "com.android.library"
    const val KOTLIN_ANDROID = "kotlin-android"
    const val KOTLIN_KAPT = "kotlin-kapt"
    const val HILT_ANDROID_PLUGIN = "dagger.hilt.android.plugin"
    const val NAVIGATION_SAFEARGS_KOTLIN = "androidx.navigation.safeargs.kotlin"
}

object GradleLibraries {
    const val GRADLE = "com.android.tools.build:gradle:${Versions.GRADLE}"
    const val KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
    const val NAVIGATION_SAFE_ARGS_GRADLE_PLUGIN =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.NAVIGATION_SAFE_ARGS_GRADLE_PLUGIN}"
    const val HILT_ANDROID_GRADLE_PLUGIN =
        "com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}"
}

object Libraries {
    const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
    const val APPCOMPAT = "androidx.appcompat:appcompat:${Versions.APPCOMPAT}"
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
    const val JUNIT = "junit:junit:${Versions.JUNIT}"
    const val TEST_JUNIT = "androidx.test.ext:junit:${Versions.TEST_JUNIT}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
    const val KOTLINX_COROUTINES_ANDROID =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KOTLINX_COROUTINES_ANDROID}"
    const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
    const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
    const val KOTLIN_STBLIB = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN}"
    const val CONSTRAINTLAYOUT =
        "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINTLAYOUT}"
    const val NAVIGATION_FRAGMENT_KTX =
        "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION_FRAGMENT_KTX}"
    const val NAVIGATION_UI_KTX =
        "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION_UI_KTX}"
    const val LIFECYCLE_LIVEDATA_KTX =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE_LIVEDATA_KTX}"
    const val LIFECYCLE_VIEWMODEL_KTX =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE_VIEWMODEL_KTX}"
    const val LEGACY_SUPPORT_V4 = "androidx.legacy:legacy-support-v4:${Versions.LEGACY_SUPPORT_V4}"
    const val DATABINDING_COMPILER =
        "com.android.databinding:compiler:${Versions.DATABINDING_COMPILER}"
    const val GSON = "com.google.code.gson:gson:${Versions.GSON}"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_CONVERTER_GSON =
        "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT_CONVERTER_GSON}"
    const val LOGGING_INTERCEPTOR =
        "com.squareup.okhttp3:logging-interceptor:${Versions.LOGGING_INTERCEPTOR}"
}

object FlavorDimensions {
    const val DEFAULT_DIMENSION = "DEFAULT_DIMENSION"
}

object ProductFlavors {
    const val DEFAULT_FLAVOR = "defaultFlavor"
    const val DATA_ANALYSIS_REPO = "dataAnalysisRepo"
    const val REST_REPO = "restRepo"
}

object Modules {
    const val APP = ":app"
    const val REPO = ":repo"
    const val DOMAIN = ":domain"
}