package dev.anthonyhfm.kowe.clients

import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import dev.anthonyhfm.kowe.ui.AndroidWebViewState

class KoweWebChromeClient(
    val state: AndroidWebViewState
) : WebChromeClient() {
    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        val koweConsoleMessage = when (consoleMessage?.messageLevel()) {
            ConsoleMessage.MessageLevel.LOG -> {
                dev.anthonyhfm.kowe.data.ConsoleMessage.Log(consoleMessage.message())
            }
            ConsoleMessage.MessageLevel.WARNING -> {
                dev.anthonyhfm.kowe.data.ConsoleMessage.Warning(consoleMessage.message())
            }
            ConsoleMessage.MessageLevel.ERROR -> {
                dev.anthonyhfm.kowe.data.ConsoleMessage.Error(consoleMessage.message())
            }
            ConsoleMessage.MessageLevel.TIP -> {
                dev.anthonyhfm.kowe.data.ConsoleMessage.Info(consoleMessage.message())
            }

            else -> null
        }

        if (koweConsoleMessage != null) {
            state.onConsoleMessage(koweConsoleMessage)
        }

        return super.onConsoleMessage(consoleMessage)
    }
}