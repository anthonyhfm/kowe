package dev.anthonyhfm.kowe.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitInteropInteractionMode
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun WebView(
    state: WebViewState,
    modifier: Modifier
) {
    UIKitView(
        modifier = modifier,
        factory = {
            return@UIKitView (state as AppleWebViewState).wkWebView
        },
        properties = UIKitInteropProperties(
            interactionMode = UIKitInteropInteractionMode.NonCooperative
        )
    )
}