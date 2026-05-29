# AdMob to AppLovin MAX Migration Plan

Replace the existing AdMob monetization layer with AppLovin MAX SDK to improve mediation efficiency and ad performance.

## Proposed Changes

### Build Configuration

#### [libs.versions.toml](file:///C:/Users/NGETICH/FootballPulse%20Live/gradle/libs.versions.toml)
- Added `applovin` version (12.4.2) and `applovin-sdk` library.
- Removed `play-services-ads`.

#### [build.gradle.kts](file:///C:/Users/NGETICH/FootballPulse%20Live/app/build.gradle.kts)
- Swapped AdMob dependency for AppLovin SDK.

---

### SDK Initialization

#### [NEW] [AppLovinInitializer.kt](file:///C:/Users/NGETICH/FootballPulse%20Live/app/src/main/java/keemgames/footballcompanion/core/initialization/AppLovinInitializer.kt)
- Implements thread-safe, non-blocking initialization of AppLovin SDK.
- Sets mediation provider to "max".
- Exposes `isSdkInitialized` StateFlow for reactive UI updates.

#### [AdModule.kt](file:///C:/Users/NGETICH/FootballPulse%20Live/app/src/main/java/keemgames/footballcompanion/di/AdModule.kt)
- Updated Hilt module to provide `AppLovinInitializer` instead of the old `AdInitializer`.

#### [FootballCompanionApp.kt](file:///C:/Users/NGETICH/FootballPulse%20Live/app/src/main/java/keemgames/footballcompanion/FootballCompanionApp.kt)
- Updated to inject and trigger `AppLovinInitializer`.

---

### Ad Components (Jetpack Compose)

#### [NEW] [MaxAdaptiveBannerAd.kt](file:///C:/Users/NGETICH/FootballPulse%20Live/app/src/main/java/keemgames/footballcompanion/presentation/components/ads/MaxAdaptiveBannerAd.kt)
- Compose wrapper for `MaxAdView` with adaptive banner support and lifecycle management.

#### [NEW] [MaxNativeAdFeedView.kt](file:///C:/Users/NGETICH/FootballPulse%20Live/app/src/main/java/keemgames/footballcompanion/presentation/components/ads/MaxNativeAdFeedView.kt)
- High-performance native ad component for LazyColumns using manual rendering to match app theme.

#### [NEW] [max_native_ad_layout.xml](file:///C:/Users/NGETICH/FootballPulse%20Live/app/src/main/res/layout/max_native_ad_layout.xml)
- Custom XML layout for Native Ads matching the premium dark theme.

#### [NEW] [MaxInterstitialHelper.kt](file:///C:/Users/NGETICH/FootballPulse%20Live/app/src/main/java/keemgames/footballcompanion/presentation/components/ads/MaxInterstitialHelper.kt)
- Singleton helper for managing Interstitial ads with a 5-minute frequency throttle.

---

### UI Integration & Cleanup

#### [HomeScreen.kt](file:///C:/Users/NGETICH/FootballPulse%20Live/app/src/main/java/keemgames/footballcompanion/presentation/home/HomeScreen.kt)
- Replaced AdMob `AdaptiveBannerAd` and `ComposeNativeAd` with AppLovin MAX equivalents.

#### [AndroidManifest.xml](file:///C:/Users/NGETICH/FootballPulse%20Live/app/src/main/AndroidManifest.xml)
- Added AppLovin SDK Key metadata.
- Enabled hardware acceleration for application.
- Removed AdMob-specific properties.

#### [DELETE] `presentation/ads/` directory
- Removed legacy AdMob source files.

## Verification Plan

### Automated Tests
- Run `./gradlew assembleDebug` to ensure compilation and dependency resolution.

### Manual Verification
- Verify SDK initialization in logs.
- Check Ad unit placeholders in `HomeScreen`.
- Confirm no "AdMob" references remain in the codebase via global search.
