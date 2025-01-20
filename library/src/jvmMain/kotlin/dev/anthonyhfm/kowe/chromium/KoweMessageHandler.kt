package dev.anthonyhfm.kowe.chromium

import org.cef.browser.CefBrowser
import org.cef.browser.CefFrame
import org.cef.callback.CefQueryCallback
import org.cef.handler.CefMessageRouterHandlerAdapter

class KoweMessageRouterHandler(
    private val onMessageReceived: (String) -> Unit
) : CefMessageRouterHandlerAdapter() {
    override fun onQuery(
        browser: CefBrowser?,
        frame: CefFrame?,
        queryId: Long,
        request: String?,
        persistent: Boolean,
        callback: CefQueryCallback?
    ): Boolean {
        request?.let {
            onMessageReceived(it)
        }

        return super.onQuery(browser, frame, queryId, request, persistent, callback)
    }
}