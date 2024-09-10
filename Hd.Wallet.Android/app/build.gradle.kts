plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.OcrBanking.Android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.OCR_BanKing.myapplication"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        debug {
            resValue("string", "api_endpoint", "https://chat.hayugo.edu.vn/api/")
            resValue("string", "socket_endpoint", "https://chat.hayugo.edu.vn/")
            resValue("string", "storage_api_endpoint", "https://storage.pgonevn.com/api/bucket/665084baa340536c521c22b1/")
            resValue("string", "storage_host", "https://storage.pgonevn.com/")
        }


        release {
            resValue("string", "api_endpoint", "https://chat.hayugo.edu.vn/api/")
            resValue("string", "socket_endpoint", "https://chat.hayugo.edu.vn/")
            resValue("string", "storage_api_endpoint", "https://storage.pgonevn.com/api/bucket/665084baa340536c521c22b1/")
            resValue("string", "storage_host", "https://storage.pgonevn.com/")


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
}

dependencies {
    implementation(libs.lottie.v280)
    implementation("com.robertlevonyan.view:MaterialChipView:3.0.8")

    val cameraxVersion = "1.3.0"
    implementation("com.google.mlkit:barcode-scanning:17.3.0")
    implementation("androidx.camera:camera-camera2:$cameraxVersion")
    implementation("androidx.camera:camera-lifecycle:$cameraxVersion")
    implementation("androidx.camera:camera-core:$cameraxVersion")
    implementation("androidx.camera:camera-video:$cameraxVersion")
    implementation("androidx.camera:camera-view:$cameraxVersion")
    implementation("androidx.camera:camera-mlkit-vision:1.5.0-alpha01")
    implementation("androidx.camera:camera-extensions:$cameraxVersion")

    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation(libs.android.lottie)
    implementation(libs.glide)
    implementation(libs.legacy.support.v4)
    implementation(libs.annotation)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
    implementation(libs.circleimageview)
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.8.5")
    implementation(libs.core)
    implementation("com.hbb20:ccp:2.6.0")
    implementation(libs.rxandroid)
    implementation("io.reactivex.rxjava3:rxjava:3.0.0")
    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.7.1")
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}