package keemgames.footballcompanion.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import keemgames.footballcompanion.domain.use_case.GetLiveMatchesUseCase
import keemgames.footballcompanion.domain.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLiveMatchesUseCase: GetLiveMatchesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        getMatches()
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
}
