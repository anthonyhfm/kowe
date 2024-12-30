package dev.anthonyhfm.compose.web.data

/**
 * # WebConfig
 *
 * The [WebConfig] lets you enable and disable features for the webview.
 */
data class WebConfig(
    /**
     * # JavaScript Support
     *
     * Enable or disable the ability for websites to run JavaScript code
     */
    val enableJavaScript: Boolean = true,
    /**
     * # JavaScript Logging
     * Shows javascript logs like `console.log` or `console.error` in the debug console.
     */
    val logForwarding: Boolean = false
)