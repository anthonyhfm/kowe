package dev.anthonyhfm.compose.web.ui

import android.content.Context
import android.util.Log
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import dev.anthonyhfm.compose.web.data.URLPolicy
import dev.anthonyhfm.compose.web.data.WebConfig

class AndroidWebViewState(
    context: Context
) : WebViewState {
    var webkit: WebView = WebView(context)

    override var config: WebConfig = WebConfig()
        set(value) {
            webkit.settings.javaScriptEnabled = value.enableJavaScript

            field = value
        }

    override val title: MutableState<String?>
        get() {
            return mutableStateOf(webkit.title)
        }

    override var policy: URLPolicy? = null

    override fun evaluateJavaScript(js: String) {
        webkit.evaluateJavascript(js) {
            Log.v("jsCallback", js)
        }
    }

    override fun navigateUrl(url: String) {
        location.value = url

        webkit.loadUrl(url)
    }

    override fun navigateHtml(html: String) {
        location.value = "about:blank"

        webkit.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
    }

    override fun reload() {
        webkit.reload()
    }
}

@Composable
@JvmName("rememberWebViewHtmlState")
actual fun rememberWebViewState(
    html: String?,
    urlPolicy: URLPolicy?,
): WebViewState {
    val context = LocalContext.current
    val state = remember {
        AndroidWebViewState(context)
    }

    if (html != null) {
        state.navigateHtml(html)
    }

    return state
}

@Composable
@JvmName("rememberWebViewUrlState")
actual fun rememberWebViewState(
    url: String,
    urlPolicy: URLPolicy?,
): WebViewState {
    val context = LocalContext.current
    val state = remember {
        AndroidWebViewState(context)
    }

    state.navigateUrl(url)

    return state
}