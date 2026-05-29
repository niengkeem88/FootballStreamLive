package keemgames.footballcompanion.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    val themeModeFlow: Flow<String>
    val onboardingCompletedFlow: Flow<Boolean>
    
    suspend fun updateThemeMode(mode: String)
    suspend fun setOnboardingCompleted(completed: Boolean)
    suspend fun updateSelectedRegion(region: String)
}
