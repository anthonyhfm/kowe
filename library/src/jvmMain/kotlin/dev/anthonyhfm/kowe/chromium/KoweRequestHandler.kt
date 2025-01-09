package dev.anthonyhfm.kowe.chromium

import dev.anthonyhfm.kowe.data.WebPolicy
import org.cef.browser.CefBrowser
import org.cef.browser.CefFrame
import org.cef.handler.CefRequestHandlerAdapter
import org.cef.network.CefRequest

class KoweRequestHandler : CefRequestHandlerAdapter() {
    var policy: WebPolicy? = null

    override fun onBeforeBrowse(
        browser: CefBrowser?,
        frame: CefFrame?,
        request: CefRequest?,
        user_gesture: Boolean,
        is_redirect: Boolean
    ): Boolean {
        var cancel = false

        if (policy != null && request?.url != null) {
            cancel = !policy!!.allowUrl(request.url)
        }

        return cancel
    }
}