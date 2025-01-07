package dev.anthonyhfm.kowe.desktop

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.configureSwingGlobalsForCompose
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.awaitApplication
import dev.datlag.kcef.KCEF
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.system.exitProcess

/**
 * # Browser Application
 *
 * Instead of using the `application` function in your Desktop's main function, you can use [browserApplication] which handles initializing and closing the chromium engine to make WebViews work.
 *
 * The content will only get displayed when the chromium engine initialized successfully.
 *
 * Example:
 *
 * ```kotlin
 * fun main() = browserApplication {
 *     Window(
 *         onCloseRequest = ::exitApplication,
 *         title = "Your WebView Application",
 *     ) {
 *         // ...
 *     }
 * }
 * ```
 */
@OptIn(ExperimentalComposeUiApi::class)
fun browserApplication(
    exitProcessOnExit: Boolean = true,
    content: @Composable ApplicationScope.() -> Unit
) {
    if (System.getProperty("compose.application.configure.swing.globals") == "true") {
        configureSwingGlobalsForCompose()
    }

    runBlocking {
        awaitApplication {
            var initialized: Boolean by remember { mutableStateOf(false) }
            val bundleLocation = System.getProperty("compose.application.resources.dir")?.let { File(it) } ?: File(".")

            LaunchedEffect(Unit) {
                withContext(Dispatchers.IO) { // IO scope recommended but not required
                    KCEF.init(
                        builder = {
                            installDir(File(bundleLocation, "kcef-bundle")) // recommended, but not necessary

                            progress {
                                onInitialized {
                                    initialized = true
                                }
                            }
                        },
                        onError = {
                            throw Exception("Error during Chromium initialization: ${it?.message}")
                        },
                        onRestartRequired = {
                            throw Exception("Chromium Error: Restart required in order to work")
                        }
                    )
                }
            }

            if (initialized) {
                content()
            }

            DisposableEffect(Unit) {
                onDispose {
                    KCEF.disposeBlocking()
                }
            }
        }
    }

    if (exitProcessOnExit) {
        exitProcess(0)
    }
}