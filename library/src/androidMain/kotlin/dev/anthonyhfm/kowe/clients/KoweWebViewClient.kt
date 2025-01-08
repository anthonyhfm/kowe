package dev.anthonyhfm.kowe.clients

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import dev.anthonyhfm.kowe.ui.AndroidWebViewState

class KoweWebViewClient(
    val state: AndroidWebViewState
) : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        if (state.policy != null) {
            val decision = state.policy!!.decidePolicyFor(request?.url.toString())

            return !decision // If the decision is "true" it will return false because it should not override.
        } else {
            return super.shouldOverrideUrlLoading(view, request)
        }
    }
}