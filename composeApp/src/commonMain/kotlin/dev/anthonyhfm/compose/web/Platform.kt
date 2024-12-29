package dev.anthonyhfm.compose.web

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform