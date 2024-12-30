package dev.anthonyhfm.compose.web.core

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import dev.datlag.kcef.KCEF
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object WebViewCore {
    val initialized: MutableState<Boolean> = mutableStateOf(false)

    suspend fun init() {
        withContext(Dispatchers.IO) {
            KCEF.init(
                builder = {
                    progress {
                        onInitialized {
                            initialized.value = true
                        }
                    }
                },
                onError = {
                    it?.printStackTrace()
                }
            )
        }
    }

    fun close() {
        KCEF.disposeBlocking()

        initialized.value = false
    }
}