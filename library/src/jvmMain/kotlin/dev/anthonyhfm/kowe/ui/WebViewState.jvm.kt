package dev.anthonyhfm.kowe.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.anthonyhfm.kowe.chromium.KoweLifeSpanHandler
import dev.anthonyhfm.kowe.chromium.KoweLoadHandler
import dev.anthonyhfm.kowe.chromium.KoweMessageRouterHandler
import dev.anthonyhfm.kowe.chromium.KoweRequestHandler
import dev.anthonyhfm.kowe.data.ConsoleMessage
import dev.anthonyhfm.kowe.data.JavaScriptResult
import dev.anthonyhfm.kowe.data.WebConfig
import dev.anthonyhfm.kowe.data.WebLoadingState
import dev.anthonyhfm.kowe.data.WebPolicy
import dev.anthonyhfm.library.generated.resources.Res
import dev.datlag.kcef.KCEF
import dev.datlag.kcef.KCEFBrowser
import dev.datlag.kcef.KCEFClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import org.cef.browser.CefMessageRouter
import org.cef.browser.CefRendering
import org.jetbrains.compose.resources.ExperimentalResourceApi

class ChromiumWebViewState(
    url: String? = null,
    html: String? = null
) : WebViewState {
    override val title: String? = null
    override var location: String?
        get() {
            return browser.url
        }
        set(value) {
            browser.loadURL(value)
        }

    private val client: KCEFClient = KCEF.newClientBlocking()

    private val lifeSpanHandler = KoweLifeSpanHandler()
    private val requestHandler = KoweRequestHandler()
    private val messageHandler = CefMessageRouter.create(
        KoweMessageRouterHandler(
            onMessageReceived = {
                onMessageReceived(it)
            }
        )
    )

    private val _loadingState: MutableStateFlow<WebLoadingState> = MutableStateFlow(WebLoadingState.Unknown)
    override val loadingState: StateFlow<WebLoadingState> = _loadingState.asStateFlow()

    private val loadHandler = KoweLoadHandler(_loadingState, this)

    init {
        client.addLifeSpanHandler(lifeSpanHandler)
        client.addRequestHandler(requestHandler)
        client.addLoadHandler(loadHandler)
    }

    override var policy: WebPolicy? = null
        set(value) {
            lifeSpanHandler.policy = value
            requestHandler.policy = value

            field = value
        }

    var browser: KCEFBrowser = if (url != null) {
        client.createBrowser(
            url = url,
            rendering = CefRendering.DEFAULT,
            isTransparent = false
        )
    } else {
        client.createBrowser(
            url = if (html == null) html else "data:text/html,$html",
            rendering = CefRendering.DEFAULT,
            isTransparent = false
        )
    }

    override var config: WebConfig = WebConfig()
        set(value) {
            // TODO: Apply config on Desktop

            if (value.enableJsBridge) {
                addBridge()
            } else {
                removeBridge()
            }

            field = value
        }

    override var onPageStart: (String?) -> Unit = { }
    override var onPageFinish: (String?) -> Unit = { }
    override var onPageError: (WebLoadingState.Error) -> Unit = { }
    override var onMessageReceived: (String) -> Unit = { }
    override var onConsoleMessage: (ConsoleMessage) -> Unit = { }

    override fun evaluateJavaScript(js: String): JavaScriptResult {
        var result: String?

        runBlocking(Dispatchers.IO) {
            result = browser.evaluateJavaScript(js)
        }

        return JavaScriptResult(result)
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

    private fun addBridge() {
        client.addMessageRouter(messageHandler)

        browser.evaluateJavaScript(INJECTION_SCRIPT) { }
    }

    private fun removeBridge() {
        client.removeMessageRouter(messageHandler)

        browser.evaluateJavaScript("window.kowe = {};") { }
    }

    @OptIn(ExperimentalResourceApi::class)
    internal val INJECTION_SCRIPT = runBlocking { Res.readBytes("files/bridge_injection.js").decodeToString() }
}

@JvmName("rememberWebViewHtmlState")
@Composable
actual fun rememberWebViewState(
    html: String?,
    config: WebConfig,
    policy: WebPolicy?
): WebViewState {
    val state = remember {
        ChromiumWebViewState(
            html = html
        )
    }

    state.config = config
    state.policy = policy

    return state
}

@JvmName("rememberWebViewUrlState")
@Composable
actual fun rememberWebViewState(
    url: String,
    config: WebConfig,
    policy: WebPolicy?,
) : WebViewState {
    val state = remember {
        ChromiumWebViewState(
            url = url
        )
    }

    state.config = config
    state.policy = policy

    return state
}