package keemgames.footballcompanion.presentation.settings

data class SettingsState(
    val themeMode: String = "system",
    val onboardingCompleted: Boolean = false
)
