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

    /**
     * # Inline Videos
     *
     * Automatically starts playing videos inline
     */
    val inlineVideos: Boolean = false,

    /**
     * # User-Agent
     *
     * Configure the User-Agent of your webview which websites can use to identify your application.
     *
     * Learn more about User-Agents [here](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/User-Agent)
     */
    val userAgent: String? = null
)