package dev.anthonyhfm.kowe.chromium

import dev.anthonyhfm.kowe.data.WebPolicy
import org.cef.browser.CefBrowser
import org.cef.browser.CefFrame
import org.cef.handler.CefLifeSpanHandlerAdapter

class KoweLifeSpanHandler : CefLifeSpanHandlerAdapter() {
    var policy: WebPolicy? = null

    /**
     * # onBeforePopup
     *
     * Normally this function decides whether or not a browser popup should open. I do not want that, but I might make it configurable in a newer version of Kowe.
     *
     * It now checks for your web policy and then sends the url back to the webview to open it in the same view.
     */
    override fun onBeforePopup(
        browser: CefBrowser?,
        frame: CefFrame?,
        target_url: String?,
        target_frame_name: String?
    ): Boolean {
        if (policy != null && target_url != null) {
            if (policy!!.allowUrl(target_url)) {
                browser?.loadURL(target_url)
            }
        } else {
            browser?.loadURL(target_url)
        }

        return true // <-- This will prevent popups to open
    }
}