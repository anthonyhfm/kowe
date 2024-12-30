package dev.anthonyhfm.compose.web.data

class DomainFilterPolicy(
    /**
     * Domain list for webview url policy
     *
     * Example Content: example.com, google.com, ...
     */
    val domains: List<String>
) : URLPolicy {
    override fun decidePolicyFor(url: String): Boolean {
        val domain = extractDomainFromUrl(url)

        return domains.any { it.equals(domain, ignoreCase = true) }
    }

    private fun extractDomainFromUrl(url: String): String {
        val regex = Regex("""^(?:https?://)?(?:www\.)?([^/]+)""")
        val matchResult = regex.find(url)

        return matchResult?.groups?.get(1)?.value ?: ""
    }
}