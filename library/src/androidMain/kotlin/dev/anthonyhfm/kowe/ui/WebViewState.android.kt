package dev.anthonyhfm.kowe.ui

import android.content.Context
import android.util.Log
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import dev.anthonyhfm.kowe.data.WebConfig
import dev.anthonyhfm.kowe.data.WebPolicy

class AndroidWebViewState(
    context: Context
) : WebViewState {
    var webkit: WebView = WebView(context)

    override var location: String? = null
    override var policy: WebPolicy? = null
    override var title: String? = null
        get() {
            return webkit.title
        }

    override var config: WebConfig = WebConfig()
        set(value) {
            webkit.settings.javaScriptEnabled = value.enableJavaScript

            field = value
        }

    override fun evaluateJavaScript(js: String) {
        webkit.evaluateJavascript(js) {
            Log.v("jsCallback", js)
        }
    }

    override fun loadUrl(url: String) {
        location = url

        webkit.loadUrl(url)
    }

    override fun loadHtml(html: String) {
        location = "about:blank"

        webkit.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
    }

    override fun reload() {
        webkit.reload()
    }
}

@JvmName("rememberWebViewHtmlState")
@Composable
actual fun rememberWebViewState(
    html: String?,
    policy: WebPolicy?
): WebViewState {
    val context = LocalContext.current
    val state = remember {
        AndroidWebViewState(context)
    }

    state.policy = policy

    if (html != null) {
        state.loadHtml(html)
    }

    return state
}

@JvmName("rememberWebViewUrlState")
@Composable
actual fun rememberWebViewState(
    url: String,
    policy: WebPolicy?,
) : WebViewState {
    val context = LocalContext.current
    val state = remember {
        AndroidWebViewState(context)
    }

    state.policy = policy
    state.loadUrl(url)

    return state
}