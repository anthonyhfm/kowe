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
     * this function can be overwritten to create custom url rules
     */
    fun decidePolicyFor(url: String): Boolean
}