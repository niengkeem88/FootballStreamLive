# --- General Android & Project Rules ---
-keepattributes SourceFile,LineNumberTable
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes EnclosingMethod
-keepattributes InnerClasses

# --- App Specific: Clean Architecture Models ---
# Keep DTOs and Entities for Reflection (Gson/Room)
-keep class keemgames.footballcompanion.data.remote.dto.** { *; }
-keep class keemgames.footballcompanion.data.local.entity.** { *; }
-keep class keemgames.footballcompanion.domain.model.** { *; }

# --- Retrofit & OkHttp ---
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keepclassmembernames interface * {
    @retrofit2.http.* <methods>;
}
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**

# --- Gson ---
-keep class com.google.gson.** { *; }
-keep class com.google.gson.reflect.TypeToken
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# --- Room ---
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.**

# --- Hilt / Dagger ---
-keep class dagger.hilt.** { *; }
-keep class * {
    @dagger.hilt.android.lifecycle.HiltViewModel <init>(...);
}

# --- AppLovin MAX ---
-keep class com.applovin.** { *; }
-dontwarn com.applovin.**

# --- Firebase ---
-keep class com.google.firebase.** { *; }

# --- Compose ---
-keep class androidx.compose.** { *; }
