package dev.anthonyhfm.kowe.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun WebView(
    state: WebViewState,
    modifier: Modifier = Modifier
)