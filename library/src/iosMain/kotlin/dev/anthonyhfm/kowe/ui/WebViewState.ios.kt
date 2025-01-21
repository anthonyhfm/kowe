package dev.anthonyhfm.kowe.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.anthonyhfm.kowe.data.ConsoleMessage
import dev.anthonyhfm.kowe.data.JavaScriptResult
import dev.anthonyhfm.kowe.data.WebConfig
import dev.anthonyhfm.kowe.data.WebLoadingState
import dev.anthonyhfm.kowe.data.WebPolicy
import dev.anthonyhfm.kowe.ui.webkit.AppleWebViewCoordinator
import dev.anthonyhfm.kowe.ui.webkit.BridgeMessageHandler
import dev.anthonyhfm.library.generated.resources.Res
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.ExperimentalResourceApi
import platform.CoreGraphics.CGRectZero
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKUserScript
import platform.WebKit.WKUserScriptInjectionTime
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import platform.WebKit.javaScriptEnabled

@OptIn(ExperimentalForeignApi::class)
class AppleWebViewState : WebViewState {
    var wkWebView: WKWebView

    override val title: String?
        get() { return wkWebView.title }

    override var location: String?
        get() {
            return wkWebView.URL?.absoluteString
        }
        set(value) {
            if (value != null) {
                wkWebView.loadRequest(NSURLRequest(NSURL(string = value)))
            }
        }

    private val _loadingState: MutableStateFlow<WebLoadingState> = MutableStateFlow(WebLoadingState.Unknown)
    override val loadingState: StateFlow<WebLoadingState> = _loadingState.asStateFlow()

    private val coordinator = AppleWebViewCoordinator(_loadingState, this)

    override var config: WebConfig = WebConfig()
        set(value) {
            wkWebView.configuration.preferences.javaScriptEnabled = value.enableJavaScript
            wkWebView.customUserAgent = value.userAgent
            wkWebView.configuration.allowsInlineMediaPlayback = value.allowsInlineMediaPlayback

            if (value.enableJsBridge) {
                addBridge()
            } else {
                removeBridge()
            }

            field = value
        }

    override var policy: WebPolicy? = null
        set(value) {
            coordinator.policy = value

            field = value
        }

    init {
        val configuration = WKWebViewConfiguration()

        wkWebView = WKWebView(
            frame = CGRectZero.readValue(),
            configuration = configuration
        )

        wkWebView.navigationDelegate = coordinator
        wkWebView.UIDelegate = coordinator
    }

    override var onPageStart: (String?) -> Unit = { }
    override var onPageFinish: (String?) -> Unit = { }
    override var onPageError: (WebLoadingState.Error) -> Unit = { }
    override var onMessageReceived: (String) -> Unit = { }
    override var onConsoleMessage: (ConsoleMessage) -> Unit = { }

    override fun evaluateJavaScript(js: String): JavaScriptResult {
        var callbackResult: String? = null

        wkWebView.evaluateJavaScript(
            javaScriptString = js,
            completionHandler = { result, error ->
                callbackResult = result as? String?
            }
        )

        return JavaScriptResult(result = callbackResult)
    }

    override fun loadUrl(url: String) {
        location = url

        wkWebView.loadRequest(NSURLRequest(NSURL(string = url)))
    }

    override fun loadHtml(html: String) {
        location = null

        wkWebView.loadHTMLString(string = html, baseURL = null)
    }

    override fun reload() {
        wkWebView.reload()
    }

    @OptIn(ExperimentalResourceApi::class)
    private fun addBridge() {
        println("Injecting Kowe Bridge")

        val script = WKUserScript(
            source = runBlocking {
                Res.readBytes("files/bridge_injection.js").decodeToString()
            },
            injectionTime = WKUserScriptInjectionTime.WKUserScriptInjectionTimeAtDocumentStart,
            forMainFrameOnly = false
        )

        wkWebView.configuration.userContentController.addUserScript(script)

        wkWebView.configuration.userContentController.addScriptMessageHandler(
            scriptMessageHandler = BridgeMessageHandler(
                onMessageReceived = {
                    onMessageReceived(it)
                }
            ),
            name = "kowe"
        )
    }

    private fun removeBridge() {
        wkWebView.configuration.userContentController.removeScriptMessageHandlerForName("kowe")

        wkWebView.configuration.userContentController.removeAllUserScripts()
    }
}

@Composable
actual fun rememberWebViewState(
    html: String?,
    config: WebConfig,
    policy: WebPolicy?
): WebViewState {
    val state = remember {
        AppleWebViewState()
    }

    state.config = config
    state.policy = policy

    if (html != null) {
        state.loadHtml(html)
    }

    return state
}

@Composable
actual fun rememberWebViewState(
    url: String,
    config: WebConfig,
    policy: WebPolicy?,
) : WebViewState {
    val state = remember {
        AppleWebViewState()
    }

    state.config = config
    state.policy = policy

    state.loadUrl(url)

    return state
}