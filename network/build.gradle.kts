plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
//    id("org.jetbrains.kotlin.plugin.parcelize")
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp.library)
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}
//ksp {
//    arg("rxhttp_package", "rxhttp")  //指定RxHttp类包名，可随意指定
//}
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
//    implementation(libs.protobuf.java) // 自己加最新版本
    implementation(libs.protobuf.kotlin) // 自己加最新版本

    implementation(libs.retrofit.mock)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.protobuf)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":common"))
    implementation(project(":proto"))
    implementation("com.github.liujingxing.rxhttp:rxhttp:3.5.0")
    implementation("com.github.liujingxing.rxhttp:converter-serialization:3.5.0")
    implementation("com.github.liujingxing.rxhttp:converter-fastjson:3.5.0")
    implementation("com.github.liujingxing.rxhttp:converter-jackson:3.5.0")
    implementation("com.github.liujingxing.rxhttp:converter-moshi:3.5.0")
    implementation("com.github.liujingxing.rxhttp:converter-protobuf:3.5.0")
    implementation("com.github.liujingxing.rxhttp:converter-simplexml:3.5.0")
//    implementation("com.github.liangjingkanji:Net:3.7.0")
    // ksp/kapt/annotationProcessor choose one
    ksp("com.github.liujingxing.rxhttp:rxhttp-compiler:3.5.0")
}