package dev.anthonyhfm.kowe.clients

import android.graphics.Bitmap
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import dev.anthonyhfm.kowe.data.WebLoadingState
import dev.anthonyhfm.kowe.ui.AndroidWebViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KoweWebViewClient(
    val state: AndroidWebViewState
) : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        if (state.policy != null) {
            val decision = state.policy!!.allowUrl(request?.url.toString())

            return !decision // If the decision is "true" it will return false because it should not override.
        } else {
            return super.shouldOverrideUrlLoading(view, request)
        }
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        CoroutineScope(Dispatchers.IO).launch {
            state._loadingState.emit(WebLoadingState.Loading)
        }

        super.onPageStarted(view, url, favicon)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            state._loadingState.emit(WebLoadingState.Finished)
        }

        super.onPageFinished(view, url)
    }
}