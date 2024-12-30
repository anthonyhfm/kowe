package dev.anthonyhfm.compose.web.data

interface URLPolicy {
    fun decidePolicyFor(url: String): Boolean
}