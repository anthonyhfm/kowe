package dev.anthonyhfm.kowe.ui

import androidx.compose.runtime.Composable
import dev.anthonyhfm.kowe.data.WebConfig
import dev.anthonyhfm.kowe.data.WebPolicy
import dev.datlag.kcef.KCEF
import dev.datlag.kcef.KCEFBrowser
import dev.datlag.kcef.KCEFClient
import org.cef.browser.CefRendering

class ChromiumWebViewState(
    url: String? = null,
    html: String? = null
) : WebViewState {
    override var location: String? = null
    override var policy: WebPolicy? = null
    override var title: String? = null

    val client: KCEFClient = KCEF.newClientBlocking()

    var browser: KCEFBrowser = if (url != null) {
        client.createBrowser(url, CefRendering.DEFAULT, false)
    } else {
        client.createBrowser(
            url = if (html == null) html else "data:text/html,$html",
            rendering = CefRendering.DEFAULT,
            isTransparent = false
        )
    }

    override var config: WebConfig = WebConfig()
        set(value) {
            println("Config currently does not apply on desktop")

            field = value
        }

    override fun evaluateJavaScript(js: String) {
        browser.executeJavaScript(js, null, 0)
    }

    override fun loadUrl(url: String) {
        location = url

        browser.loadURL(url)
    }

    override fun loadHtml(html: String) {
        location = "about:blank"

        browser.loadURL("data:text/html,$html")
    }

    override fun reload() {
        browser.reload()
    }
}

@JvmName("rememberWebViewHtmlState")
@Composable
actual fun rememberWebViewState(
    html: String?,
    policy: WebPolicy?
): WebViewState {
    TODO("Not yet implemented")
}

@JvmName("rememberWebViewUrlState")
@Composable
actual fun rememberWebViewState(
    url: String,
    policy: WebPolicy?,
) : WebViewState {
    TODO("Not yet implemented")
}