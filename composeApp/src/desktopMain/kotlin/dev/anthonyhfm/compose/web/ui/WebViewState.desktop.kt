package dev.anthonyhfm.compose.web.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.anthonyhfm.compose.web.data.URLPolicy
import dev.datlag.kcef.KCEF
import dev.datlag.kcef.KCEFBrowser
import dev.datlag.kcef.KCEFClient
import org.cef.browser.CefRendering
import java.net.URL

class DesktopWebViewState : WebViewState {
    val client: KCEFClient = KCEF.newClientBlocking()
    var browser: KCEFBrowser

    constructor(url: String? = null, html: String? = null) {
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

    override val title: String?
        get() = null

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
actual fun rememberWebViewState(html: String?): WebViewState {
    val state = remember {
        DesktopWebViewState(
            html = html
        )
    }

    return state
}

@Composable
actual fun rememberWebViewState(url: String): WebViewState {
    val state = remember {
        DesktopWebViewState(
            url = url
        )
    }

    return state
}