# Compose WebView

The **Compose WebView Library** allows you to integrate WebViews into your **Compose Multiplatform** app, providing access to native WebView components on each platform, such as `android.webkit` for Android and `WKWebView` for iOS. This enables you to seamlessly display web content within your app, using the native WebView implementations of each platform with a unified API to ensure optimal performance and a great user and developer experience.

## Usage

### Installation

***To be done ⚠️***

### Using the WebView Component

#### Desktop Setup

For using the Compose WebView Library in your Compose for Desktop Application you need to do some specific adjustments.

As seen in the example down below, you will need to go to the root of your Desktop Application and initialize the Web Core. You will also need to only render WebView-States and WebView Composables when the initialization is done.
This is due to [***KCEF***](https://github.com/DatL4g/KCEF) running the Chromium Engine on Desktop. It takes a little time to start and can not be accessed while initializing.

```kotlin
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose WebView",
    ) {
        // Initializing the Chromium WebView
        LaunchedEffect(Unit) {
            WebViewCore.init()
        }
  
        // Only display WebView implementations and state initializations when Chromium initialized
        if (WebViewCore.initialized.value) {
            App()
        }
    }
}
```

> [!NOTE]
> You will also need the newest version of the [JetBrains Runtime with JCEF](https://github.com/JetBrains/JetBrainsRuntime/releases) and follow the KCEF specific [gradle setup](https://github.com/DatL4g/KCEF/blob/master/COMPOSE.md#flags) instructions. 

---

#### General Usage

To use the WebView component you need to create a WebViewState and the Composable itself. Here is a small example:

```kotlin
@Composable
fun App() {
    val state = rememberWebViewState(url = "https://example.com")
  
    WebView(
        state = state,
        modifier = Modifier
            .fillMaxSize()
    )
}
```

## Support

Compose WebView currently only supports Android and iOS but I am also working on a Desktop and Web implementation.

Implementations:
| Android | iOS | Desktop |
| ------- | --- | ------- |
| android.webkit | WKWebView | [***Jetbrains Runtime, KCEF***](https://github.com/JetBrains/JetBrainsRuntime?tab=readme-ov-file#why-use-jetbrains-runtime) |

> [!NOTE]
> In order to use the **Compose WebView Library** in a Compose for Desktop Application you need to run your application with a JCEF build of the [**JetBrains Runtime**](https://github.com/JetBrains/JetBrainsRuntime). You also need to do some additional adjustment to your gradle setup for [***KCEF***](https://github.com/DatL4g/KCEF/blob/master/COMPOSE.md#flags) 

---

## Project Overview

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),

If you face any issues, please report them on [GitHub](https://github.com/JetBrains/compose-multiplatform/issues).