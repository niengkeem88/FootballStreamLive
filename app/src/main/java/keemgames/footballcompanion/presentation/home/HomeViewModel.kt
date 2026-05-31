package keemgames.footballcompanion.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import keemgames.footballcompanion.data.preferences.SupportedLanguage
import keemgames.footballcompanion.domain.repository.PreferencesRepository
import keemgames.footballcompanion.domain.use_case.GetLiveMatchesUseCase
import keemgames.footballcompanion.domain.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLiveMatchesUseCase: GetLiveMatchesUseCase,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _selectedLanguage = MutableStateFlow(SupportedLanguage.EN)
    val selectedLanguage: StateFlow<SupportedLanguage> = _selectedLanguage.asStateFlow()

    init {
        getMatches()
        viewModelScope.launch {
            preferencesRepository.selectedLanguageFlow.collect { code ->
                _selectedLanguage.value = SupportedLanguage.fromCode(code)
            }
        }
    }

    fun getMatches() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            val result = getLiveMatchesUseCase()
            when (result) {
                is Resource.Success -> {
                    val matches = result.data ?: emptyList()
                    val initialTab = when {
                        matches.any { it.category.name == "LIVE" } -> 0
                        matches.any { it.category.name == "UPCOMING" } -> 1
                        else -> 2
                    }
                    _state.value = _state.value.copy(
                        allMatches = matches,
                        isLoading = false,
                        error = null,
                        selectedTab = initialTab
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }
    }

    fun selectTab(index: Int) {
        _state.value = _state.value.copy(selectedTab = index)
    }

    fun updateSearchQuery(query: String) {
        _state.value = _state.value.copy(searchQuery = query)
    }

    fun setDateFilter(filter: DateFilterOption) {
        _state.value = _state.value.copy(dateFilter = filter)
    }

    fun setLanguage(language: SupportedLanguage) {
        viewModelScope.launch {
            preferencesRepository.setSelectedLanguage(language.code)
        }
    }
}
