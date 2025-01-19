package dev.anthonyhfm.kowe.ui.webkit

import platform.WebKit.WKScriptMessage
import platform.WebKit.WKScriptMessageHandlerProtocol
import platform.WebKit.WKUserContentController
import platform.darwin.NSObject

class BridgeMessageHandler(
    private val onMessageReceived: (String) -> Unit
) : NSObject(), WKScriptMessageHandlerProtocol {
    override fun userContentController(
        userContentController: WKUserContentController,
        didReceiveScriptMessage: WKScriptMessage
    ) {
        if (didReceiveScriptMessage.name == "kowe" && didReceiveScriptMessage.body as? String != null) {
            onMessageReceived(didReceiveScriptMessage.body as String)
        }
    }
}