package keemgames.footballcompanion.data.preferences

/**
 * Languages supported by TheSportsDB for descriptions.
 * Each maps to a strDescriptionXX field in the API responses.
 */
enum class SupportedLanguage(
    val code: String,
    val displayName: String,
    val apiFieldSuffix: String
) {
    EN("en", "English", "EN"),
    FR("fr", "Français", "FR"),
    DE("de", "Deutsch", "DE"),
    IT("it", "Italiano", "IT"),
    ES("es", "Español", "ES"),
    PT("pt", "Português", "PT"),
    NL("nl", "Nederlands", "NL"),
    RU("ru", "Русский", "RU"),
    JP("jp", "日本語", "JP"),
    CN("cn", "中文", "CN"),
    NO("no", "Norsk", "NO"),
    SE("se", "Svenska", "SE"),
    PL("pl", "Polski", "PL");

    companion object {
        fun fromCode(code: String): SupportedLanguage =
            entries.find { it.code == code } ?: EN
    }
}
