package com.github.typenil.hhjobs.feature.search

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state has empty query and no items`() = runTest {
        val savedStateHandle = SavedStateHandle()
        val viewModel = SearchViewModel(savedStateHandle)

        viewModel.uiState.test {
            val initial = awaitItem()
            assertEquals("", initial.query)
            assertTrue(initial.items.isEmpty())
            assertFalse(initial.isLoading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `query change updates results after debounce`() = runTest {
        val savedStateHandle = SavedStateHandle()
        val viewModel = SearchViewModel(savedStateHandle)

        viewModel.uiState.test {
            val initial = awaitItem()
            assertEquals("", initial.query)

            viewModel.onQueryChange("Android")

            // Advance past debounce (400ms) + simulated delay (200ms)
            advanceTimeBy(700L)
            advanceUntilIdle()

            val latest = expectMostRecentItem()
            assertEquals("Android", latest.query)
            assertFalse(latest.isLoading)
            assertTrue(latest.items.isNotEmpty())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `clear query resets search state`() = runTest {
        val savedStateHandle = SavedStateHandle(mapOf("search_query" to "Android"))
        val viewModel = SearchViewModel(savedStateHandle)

        viewModel.uiState.test {
            advanceTimeBy(700L)
            advanceUntilIdle()

            viewModel.onClearQuery()
            advanceTimeBy(500L)
            advanceUntilIdle()

            val state = expectMostRecentItem()
            assertEquals("", state.query)
            assertTrue(state.items.isEmpty())

            cancelAndIgnoreRemainingEvents()
        }
    }
}
