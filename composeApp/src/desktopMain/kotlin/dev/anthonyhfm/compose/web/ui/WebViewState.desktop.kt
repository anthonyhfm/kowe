package dev.anthonyhfm.compose.web.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import dev.anthonyhfm.compose.web.data.URLPolicy
import dev.anthonyhfm.compose.web.data.WebConfig
import dev.datlag.kcef.KCEF
import dev.datlag.kcef.KCEFBrowser
import dev.datlag.kcef.KCEFBuilder
import dev.datlag.kcef.KCEFClient
import org.cef.CefBrowserSettings
import org.cef.browser.CefRendering

class DesktopWebViewState(url: String? = null, html: String? = null) : WebViewState {
    val client: KCEFClient = KCEF.newClientBlocking()
    var browser: KCEFBrowser

    init {
        if (url != null) {
            browser = client.createBrowser(url, CefRendering.DEFAULT, false)
        } else {
            browser = client.createBrowser(
                url = if (html == null) html else "data:text/html,$html",
                rendering = CefRendering.DEFAULT,
                isTransparent = false
            )
        }
    }

    override var config: WebConfig = WebConfig()
        set(value) {
            println("Config currently does not apply on desktop")

            field = value
        }

    override val title: MutableState<String?> = mutableStateOf(null)

    override var policy: URLPolicy? = null

    override fun evaluateJavaScript(js: String) {
        browser.executeJavaScript(js, null, 0)
    }

    override fun navigateUrl(url: String) {
        location.value = url

        browser.loadURL(url)
    }

    override fun navigateHtml(html: String) {
        location.value = "about:blank"

        browser.loadURL("data:text/html,$html")
    }

    override fun reload() {
        browser.reload()
    }
}


@Composable
@JvmName("rememberWebViewHtmlState")
actual fun rememberWebViewState(
    html: String?,
    urlPolicy: URLPolicy?,
): WebViewState {
    val state = remember {
        DesktopWebViewState(
            html = html
        )
    }

    state.policy = urlPolicy

    return state
}

@Composable
@JvmName("rememberWebViewUrlState")
actual fun rememberWebViewState(
    url: String,
    urlPolicy: URLPolicy?,
): WebViewState {
    val state = remember {
        DesktopWebViewState(
            url = url
        )
    }

    state.policy = urlPolicy

    return state
}