package dev.anthonyhfm.kowe.data

sealed interface WebLoadingState {
    data object Unknown : WebLoadingState

    data object Loading : WebLoadingState
    data object Finished : WebLoadingState

    data class Error(val description: String?) : WebLoadingState
}