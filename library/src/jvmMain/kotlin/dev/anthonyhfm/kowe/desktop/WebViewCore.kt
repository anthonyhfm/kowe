package dev.anthonyhfm.kowe.desktop

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import dev.datlag.kcef.KCEF
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

object WebViewCore {
    val initialized: MutableState<Boolean> = mutableStateOf(false)

    fun init() {
        runBlocking(Dispatchers.IO) {
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