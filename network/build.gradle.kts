plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
//    id("org.jetbrains.kotlin.plugin.parcelize")
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.cxtapp.network"
    compileSdk = 36

    defaultConfig {
        minSdk = 27

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit){
        exclude(group = "com.squareup.okhttp3", module = "okhttp") // 可选，如果你想手动指定 OkHttp 最新版
    }
    implementation(libs.converter.gson){
        // 排除 gson 依赖
        exclude(group = "com.google.code.gson", module = "gson")
    }
    implementation(libs.gson) // 自己加最新版本

    implementation(libs.converter.jackson){
        exclude(group = "com.fasterxml.jackson.core", module = "jackson-databind")
        exclude(group = "com.fasterxml.jackson.core", module = "jackson-core")
        exclude(group = "com.fasterxml.jackson.core", module = "jackson-annotations")
    }
    implementation(libs.jackson.databind) // 自己加最新版本
    implementation(libs.jackson.core) // 自己加最新版本
    implementation(libs.jackson.annotations) // 自己加最新版本


    implementation(libs.converter.protobuf)
    {
        exclude(group = "com.google.protobuf", module = "protobuf-java")
    }
    implementation(libs.protobuf.java) // 自己加最新版本

    implementation(libs.retrofit.mock)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":common"))
}