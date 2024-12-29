package dev.anthonyhfm.compose.web.data

sealed interface WebLoadingState {
    data object Loading
    data object Finished
}