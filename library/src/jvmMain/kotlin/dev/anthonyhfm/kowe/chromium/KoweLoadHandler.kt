package dev.anthonyhfm.kowe.chromium

import dev.anthonyhfm.kowe.data.WebLoadingState
import dev.anthonyhfm.kowe.ui.ChromiumWebViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.cef.browser.CefBrowser
import org.cef.browser.CefFrame
import org.cef.handler.CefLoadHandler
import org.cef.handler.CefLoadHandlerAdapter
import org.cef.network.CefRequest

class KoweLoadHandler(
    private val loadingState: MutableStateFlow<WebLoadingState>,
    private val state: ChromiumWebViewState
) : CefLoadHandlerAdapter() {
    override fun onLoadStart(
        browser: CefBrowser?,
        frame: CefFrame?,
        transitionType: CefRequest.TransitionType?
    ) {
        if (state.config.enableJsBridge) {
            browser?.executeJavaScript(state.INJECTION_SCRIPT, null, 0)
        }

        CoroutineScope(Dispatchers.IO).launch {
            loadingState.emit(WebLoadingState.Loading)
        }

        super.onLoadStart(browser, frame, transitionType)
    }

    override fun onLoadEnd(browser: CefBrowser?, frame: CefFrame?, httpStatusCode: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            loadingState.emit(WebLoadingState.Finished)
        }

        super.onLoadEnd(browser, frame, httpStatusCode)
    }

    override fun onLoadError(
        browser: CefBrowser?,
        frame: CefFrame?,
        errorCode: CefLoadHandler.ErrorCode?,
        errorText: String?,
        failedUrl: String?
    ) {
        val error = WebLoadingState.Error(
            url = failedUrl,
            description = errorText,
            errorCode = errorCode?.code
        )

        CoroutineScope(Dispatchers.IO).launch {
            loadingState.emit(error)
        }

        state.onPageError(error)

        super.onLoadError(browser, frame, errorCode, errorText, failedUrl)
    }

    override fun onLoadingStateChange(
        browser: CefBrowser?,
        isLoading: Boolean,
        canGoBack: Boolean,
        canGoForward: Boolean
    ) {
        super.onLoadingStateChange(browser, isLoading, canGoBack, canGoForward)
    }
}