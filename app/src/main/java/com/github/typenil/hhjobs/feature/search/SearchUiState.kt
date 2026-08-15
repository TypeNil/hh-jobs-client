package com.github.typenil.hhjobs.feature.search

import androidx.compose.runtime.Immutable

@Immutable
data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val items: List<String> = emptyList(),
    val errorMessage: String? = null,
)

sealed interface SearchUiEvent {
    data class OnQueryChange(val query: String) : SearchUiEvent
    data object OnClearQuery : SearchUiEvent
    data object OnRefresh : SearchUiEvent
    data object OnRetry : SearchUiEvent
}
