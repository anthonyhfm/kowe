package dev.anthonyhfm.kowe.data

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
)