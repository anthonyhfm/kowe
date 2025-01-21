package dev.anthonyhfm.kowe.ui.webkit

import dev.anthonyhfm.kowe.data.WebLoadingState
import dev.anthonyhfm.kowe.data.WebPolicy
import dev.anthonyhfm.kowe.ui.AppleWebViewState
import kotlinx.cinterop.ObjCSignatureOverride
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import platform.Foundation.NSError
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKNavigation
import platform.WebKit.WKNavigationAction
import platform.WebKit.WKNavigationActionPolicy
import platform.WebKit.WKNavigationDelegateProtocol
import platform.WebKit.WKUIDelegateProtocol
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import platform.WebKit.WKWindowFeatures
import platform.darwin.NSObject
import platform.posix.err

class AppleWebViewCoordinator(
    val loadingState: MutableStateFlow<WebLoadingState>,
    val state: AppleWebViewState,
) : NSObject(), WKNavigationDelegateProtocol, WKUIDelegateProtocol {
    var policy: WebPolicy? = null

    override fun webView(
        webView: WKWebView,
        decidePolicyForNavigationAction: WKNavigationAction,
        decisionHandler: (WKNavigationActionPolicy) -> Unit
    ) {
        if (policy != null) {
            val decision = policy!!.allowUrl(decidePolicyForNavigationAction.request.URL?.absoluteString ?: "")

            if (decision) {
                decisionHandler(WKNavigationActionPolicy.WKNavigationActionPolicyAllow)
            } else {
                decisionHandler(WKNavigationActionPolicy.WKNavigationActionPolicyCancel)
            }
        } else {
            decisionHandler(WKNavigationActionPolicy.WKNavigationActionPolicyAllow)
        }
    }

    override fun webView(
        webView: WKWebView,
        createWebViewWithConfiguration: WKWebViewConfiguration,
        forNavigationAction: WKNavigationAction,
        windowFeatures: WKWindowFeatures
    ): WKWebView? {
        val targetUrl = forNavigationAction.request.URL?.absoluteString

        if (targetUrl != null) {
            webView.loadRequest(NSURLRequest(NSURL(string = targetUrl)))
        }

        return null
    }

    @ObjCSignatureOverride
    override fun webView(
        webView: WKWebView,
        didStartProvisionalNavigation: WKNavigation?,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            loadingState.emit(WebLoadingState.Loading)
        }

        state.onPageStart(webView.URL?.absoluteString)
    }

    @ObjCSignatureOverride
    override fun webView(
        webView: WKWebView,
        didFinishNavigation: WKNavigation?,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            loadingState.emit(WebLoadingState.Finished)
        }

        state.onPageFinish(webView.URL?.absoluteString)
    }

    override fun webView(
        webView: WKWebView,
        didFailNavigation: WKNavigation?,
        withError: NSError
    ) {
        val error = WebLoadingState.Error(
            url = webView.URL?.absoluteString,
            description = withError.localizedDescription,
            errorCode = null
        )

        CoroutineScope(Dispatchers.IO).launch {
            loadingState.emit(error)
        }

        state.onPageError(error)
    }
}