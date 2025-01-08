package dev.anthonyhfm.kowe.ui

import android.content.Context
import android.util.Log
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import dev.anthonyhfm.kowe.data.JavaScriptResult
import dev.anthonyhfm.kowe.data.WebConfig
import dev.anthonyhfm.kowe.data.WebPolicy

class AndroidWebViewState(
    context: Context
) : WebViewState {
    var webkit: WebView = WebView(context)

    override var location: String?
        get() {
            return webkit.url
        }
        set(value) {
            webkit.loadUrl(value ?: "about:blank")
        }

    override var policy: WebPolicy? = null
    override val title: String?
        get() { return webkit.title }

    override var config: WebConfig = WebConfig()
        set(value) {
            webkit.settings.javaScriptEnabled = value.enableJavaScript
            webkit.settings.userAgentString = value.userAgent

            field = value
        }

    override fun evaluateJavaScript(js: String): JavaScriptResult {
        var result: String? = null
        webkit.evaluateJavascript(js) {
            result = it
        }

        return JavaScriptResult(result)
    }

    override fun loadUrl(url: String) {
        location = url
    }

    override fun loadHtml(html: String) {
        location = null

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
    config: WebConfig,
    policy: WebPolicy?
): WebViewState {
    val context = LocalContext.current
    val state = remember {
        AndroidWebViewState(context)
    }

    state.config = config
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
    config: WebConfig,
    policy: WebPolicy?,
) : WebViewState {
    val context = LocalContext.current
    val state = remember {
        AndroidWebViewState(context)
    }

    state.config = config
    state.policy = policy

    state.loadUrl(url)

    return state
}