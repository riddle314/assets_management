plugins {
    id(GradlePlugins.APPLICATION)
    id(GradlePlugins.KOTLIN_ANDROID)
    id(GradlePlugins.KOTLIN_KAPT)
    id(GradlePlugins.NAVIGATION_SAFEARGS_KOTLIN)
    id(GradlePlugins.HILT_ANDROID_PLUGIN)
    id(GradlePlugins.KOTLIN_PARCELIZE)
}

android {
    compileSdk = Versions.COMPILE_SDK

    defaultConfig {
        applicationId = ProjectConfig.APPLICATION_ID
        minSdk = Versions.MIN_SDK
        targetSdk = Versions.TARGET_SDK
        versionCode = ProjectConfig.VERSION_CODE
        versionName = ProjectConfig.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }


    flavorDimensions += listOf(FlavorDimensions.DEFAULT_DIMENSION)
    productFlavors {
        create(ProductFlavors.DEFAULT_FLAVOR) {
            dimension = FlavorDimensions.DEFAULT_DIMENSION
            matchingFallbacks += listOf(ProductFlavors.DATA_ANALYSIS_REPO)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(Libraries.KOTLIN_STBLIB)
    implementation(Libraries.CORE_KTX)
    implementation(Libraries.APPCOMPAT)
    implementation(Libraries.MATERIAL)
    implementation(Libraries.CONSTRAINTLAYOUT)
    implementation(Libraries.NAVIGATION_FRAGMENT_KTX)
    implementation(Libraries.NAVIGATION_UI_KTX)
    implementation(Libraries.LIFECYCLE_LIVEDATA_KTX)
    implementation(Libraries.LIFECYCLE_VIEWMODEL_KTX)
    implementation(Libraries.LEGACY_SUPPORT_V4)

    testImplementation(Libraries.JUNIT)
    androidTestImplementation(Libraries.TEST_JUNIT)
    androidTestImplementation(Libraries.ESPRESSO_CORE)
    kapt(Libraries.DATABINDING_COMPILER)

    implementation(Libraries.KOTLINX_COROUTINES_ANDROID)

    implementation(Libraries.HILT_ANDROID)
    kapt(Libraries.HILT_ANDROID_COMPILER)

    // add the repo module
    implementation(project(Modules.REPO))

    // add the domain module
    implementation(project(Modules.DOMAIN))

}