package dev.anthonyhfm.kowe.data

/**
 * # ConsoleMessage
 *
 * Kowe supports picking up console messages from websites
 */
sealed interface ConsoleMessage {
    /**
     * # Log
     *
     * [Log] is the model which picks up javascript's `console.log` messages
     */
    data class Log(val message: String) : ConsoleMessage

    /**
     * # Info
     *
     * [Info] is the model which picks up javascript's `console.info` messages
     */
    data class Info(val message: String) : ConsoleMessage

    /**
     * # Warning
     *
     * [Warning] is the model which picks up javascript's `console.warn` messages
     */
    data class Warning(val message: String) : ConsoleMessage

    /**
     * # Error
     *
     * [Error] is the model which picks up javascript's `console.error` messages
     */
    data class Error(val message: String) : ConsoleMessage
}