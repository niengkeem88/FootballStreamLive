package keemgames.footballcompanion.data.repository

import keemgames.footballcompanion.data.preferences.AppPreferencesRepository
import keemgames.footballcompanion.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val appPreferences: AppPreferencesRepository
) : PreferencesRepository {
    override val themeModeFlow: Flow<String> = appPreferences.themeModeFlow
    override val onboardingCompletedFlow: Flow<Boolean> = appPreferences.onboardingCompletedFlow
    override val selectedLanguageFlow: Flow<String> = appPreferences.selectedLanguageFlow

    override suspend fun updateThemeMode(mode: String) {
        appPreferences.updateThemeMode(mode)
    }

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        appPreferences.setOnboardingCompleted(completed)
    }

    override suspend fun updateSelectedRegion(region: String) {
        appPreferences.updateSelectedRegion(region)
    }

    override suspend fun setSelectedLanguage(languageCode: String) {
        appPreferences.setSelectedLanguage(languageCode)
    }
}
