plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.multiscreen.final_lock"
    compileSdk = 34
    viewBinding {
        enable= true
    }
    defaultConfig {
        applicationId = "com.multiscreen.final_lock"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}


dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    dependencies {
        implementation ("androidx.appcompat:appcompat:VERSION");
        implementation("com.google.android.material:material:1.12.0")
        implementation("com.squareup.picasso:picasso:2.8")
        implementation("org.mindrot:jbcrypt:0.4")
        implementation("androidx.appcompat:appcompat:1.4.0")
        implementation("androidx.core:core-ktx:1.8.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
        implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
        implementation("androidx.navigation:navigation-fragment-ktx:2.4.2")
        implementation("androidx.navigation:navigation-ui-ktx:2.4.2")
        implementation(platform("com.google.firebase:firebase-bom:29.0.3"))
        implementation("com.google.firebase:firebase-auth-ktx")
        implementation("com.google.firebase:firebase-firestore-ktx")
        implementation("com.google.firebase:firebase-storage-ktx")
        implementation("com.google.firebase:firebase-database-ktx")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.3")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    }
}