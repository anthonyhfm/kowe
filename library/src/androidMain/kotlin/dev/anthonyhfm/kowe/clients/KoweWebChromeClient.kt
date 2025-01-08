package dev.anthonyhfm.kowe.clients

import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import dev.anthonyhfm.kowe.ui.AndroidWebViewState

class KoweWebChromeClient(
    val state: AndroidWebViewState
) : WebChromeClient() {

}