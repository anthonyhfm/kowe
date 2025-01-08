package dev.anthonyhfm.kowe.data

/**
 * # WebPolicy
 *
 * The WebPolicy can evaluate whether or not a url should be loaded or not
 */
interface WebPolicy {
    /**
     * ## decidePolicyFor
     *
     * You can use this function to create custom **URL-Rules** for your webview e.g. by only returning "true" when the url includes "jetbrains.com", therefore only urls with "jetbrains.com" can be opened in your webview.
     */
    fun decidePolicyFor(url: String): Boolean
}