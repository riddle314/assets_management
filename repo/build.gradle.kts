plugins {
    id(GradlePlugins.LIBRARY)
    id(GradlePlugins.KOTLIN_ANDROID)
    id(GradlePlugins.KOTLIN_KAPT)
    id(GradlePlugins.HILT_ANDROID_PLUGIN)
}

android {
    compileSdk = Versions.COMPILE_SDK

    defaultConfig {
        minSdk = Versions.MIN_SDK
        targetSdk = Versions.TARGET_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }
    }

    flavorDimensions += listOf(FlavorDimensions.DEFAULT_DIMENSION)
    productFlavors {
        create(ProductFlavors.DATA_ANALYSIS_REPO) {
            dimension = FlavorDimensions.DEFAULT_DIMENSION
            matchingFallbacks += listOf(ProductFlavors.DEFAULT_FLAVOR)
        }

        create(ProductFlavors.REST_REPO) {
            dimension = FlavorDimensions.DEFAULT_DIMENSION
            matchingFallbacks += listOf(ProductFlavors.DEFAULT_FLAVOR)
        }
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(Libraries.CORE_KTX)
    implementation(Libraries.APPCOMPAT)
    implementation(Libraries.MATERIAL)

    testImplementation(Libraries.JUNIT)
    androidTestImplementation(Libraries.TEST_JUNIT)
    androidTestImplementation(Libraries.ESPRESSO_CORE)

    implementation(Libraries.KOTLINX_COROUTINES_ANDROID)

    implementation(Libraries.HILT_ANDROID)
    kapt(Libraries.HILT_ANDROID_COMPILER)

    implementation(Libraries.ROOM_RUNTIME)
    kapt(Libraries.ROOM_COMPILER)

    implementation(Libraries.GSON)
    implementation(Libraries.RETROFIT)
    implementation(Libraries.RETROFIT_CONVERTER_GSON)
    implementation(Libraries.LOGGING_INTERCEPTOR)

    // add the domain module
    implementation(project(Modules.DOMAIN))
}