package com.mshdabiola.analytics

/**
 * Represents an analytics event.
 *
 * @param type - the event type. Wherever possible use one of the standard
 * event `Types`, however, if there is no suitable event type already defined, a custom event can be
 * defined as long as it is configured in your backend analytics system (for example, by creating a
 * Firebase Analytics custom event).
 *
 * @param extras - list of parameters which supply additional context to the event. See `Param`.
 */
data class AnalyticsEvent(
    val type: String,
    val extras: List<Param> = emptyList(),
) {
    // Standard analytics types.
    class Types {
        companion object {
            const val SCREEN_VIEW = "screen_view" // (extras: SCREEN_NAME)
        }
    }

    /**
     * A key-value pair used to supply extra context to an analytics event.
     *
     * @param key - the parameter key. Wherever possible use one of the standard `ParamKeys`,
     * however, if no suitable key is available you can define your own as long as it is configured
     * in your backend analytics system (for example, by creating a Firebase Analytics custom
     * parameter).
     *
     * @param value - the parameter value.
     */
    data class Param(val key: String, val value: String)

    // Standard parameter keys.
    class ParamKeys {
        companion object {
            const val SCREEN_NAME = "screen_name"
        }
    }
}
