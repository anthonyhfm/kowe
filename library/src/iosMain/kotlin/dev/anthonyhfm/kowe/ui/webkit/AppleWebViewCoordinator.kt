package dev.anthonyhfm.kowe.ui.webkit

import dev.anthonyhfm.kowe.data.WebPolicy
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKNavigationAction
import platform.WebKit.WKNavigationActionPolicy
import platform.WebKit.WKNavigationDelegateProtocol
import platform.WebKit.WKUIDelegateProtocol
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import platform.WebKit.WKWindowFeatures
import platform.darwin.NSObject

class AppleWebViewCoordinator : NSObject(), WKNavigationDelegateProtocol, WKUIDelegateProtocol {
    var policy: WebPolicy? = null

    override fun webView(
        webView: WKWebView,
        decidePolicyForNavigationAction: WKNavigationAction,
        decisionHandler: (WKNavigationActionPolicy) -> Unit
    ) {
        if (policy != null) {
            val decision = policy!!.decidePolicyFor(decidePolicyForNavigationAction.request.URL?.absoluteString ?: "")

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
}