package dev.anthonyhfm.kowe.bridge

import android.webkit.JavascriptInterface

internal class KoweMessageInterface(private val onMessage: (String) -> Unit) {
    @JavascriptInterface
    fun postMessage(message: String) {
        onMessage(message)
    }
}