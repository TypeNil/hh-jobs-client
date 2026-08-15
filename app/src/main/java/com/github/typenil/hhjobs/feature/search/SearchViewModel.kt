package com.github.typenil.hhjobs.feature.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val queryFlow = savedStateHandle.getStateFlow(KEY_SEARCH_QUERY, "")
    private val isRefreshingFlow = MutableStateFlow(false)
    private val errorMessageFlow = MutableStateFlow<String?>(null)
    private val refreshTriggerFlow = MutableStateFlow(0)

    private val searchResultsFlow = combine(
        queryFlow.debounce(DEBOUNCE_MILLIS.milliseconds).distinctUntilChanged(),
        refreshTriggerFlow,
    ) { query, _ -> query }
        .flatMapLatest { query ->
            flow {
                if (query.isBlank()) {
                    emit(SearchResult(items = emptyList(), isLoading = false))
                    return@flow
                }

                emit(SearchResult(items = emptyList(), isLoading = true))
                // Simulated search delay
                delay(SIMULATED_NETWORK_DELAY_MILLIS.milliseconds)

                val results = MOCK_DATA.filter { item ->
                    item.contains(query, ignoreCase = true)
                }

                emit(SearchResult(items = results, isLoading = false))
            }
        }

    val uiState: StateFlow<SearchUiState> = combine(
        queryFlow,
        searchResultsFlow,
        isRefreshingFlow,
        errorMessageFlow,
    ) { query, searchResult, isRefreshing, errorMessage ->
        SearchUiState(
            query = query,
            isLoading = searchResult.isLoading,
            isRefreshing = isRefreshing,
            items = searchResult.items,
            errorMessage = errorMessage,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
        initialValue = SearchUiState(query = queryFlow.value),
    )

    fun onQueryChange(newQuery: String) {
        savedStateHandle[KEY_SEARCH_QUERY] = newQuery
        errorMessageFlow.value = null
    }

    fun onClearQuery() {
        savedStateHandle[KEY_SEARCH_QUERY] = ""
        errorMessageFlow.value = null
    }

    fun onRefresh() {
        viewModelScope.launch {
            isRefreshingFlow.value = true
            refreshTriggerFlow.value += 1
            delay(REFRESH_DELAY_MILLIS.milliseconds)
            isRefreshingFlow.value = false
        }
    }

    fun onRetry() {
        errorMessageFlow.value = null
        refreshTriggerFlow.value += 1
    }

    private data class SearchResult(
        val items: List<String>,
        val isLoading: Boolean,
    )

    companion object {
        private const val KEY_SEARCH_QUERY = "search_query"
        private const val DEBOUNCE_MILLIS = 400L
        private const val STOP_TIMEOUT_MILLIS = 5_000L
        private const val SIMULATED_NETWORK_DELAY_MILLIS = 200L
        private const val REFRESH_DELAY_MILLIS = 500L

        private val MOCK_DATA = listOf(
            "Android Developer (Kotlin, Jetpack Compose) — Яндекс",
            "Senior Android Engineer — Тинькофф",
            "Junior Android Developer — VK",
            "Lead Mobile Architect — Ozon",
            "Kotlin Developer — Авито",
            "Android SDK Engineer — Сбер",
        )
    }
}
