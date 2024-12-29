package dev.anthonyhfm.compose.web.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun WebView(
    state: WebViewState,
    modifier: Modifier
) {
    UIKitView(
        modifier = modifier,
        factory = {
            val configuration = WKWebViewConfiguration()
            val webView = WKWebView(frame = CGRectZero.readValue(), configuration = configuration)

            val url = NSURL(string = state.url)

            webView.loadRequest(NSURLRequest(url))

            return@UIKitView webView
        },
        update = { webView ->
            /*state.url.let { url ->
                if (webView.URL?.absoluteString != url) {
                    val nsUrl = NSURL(string = url)
                    nsUrl.let {
                        val request = NSURLRequest(nsUrl)
                        webView.loadRequest(request)
                    }
                }
            }*/
        }
    )
}