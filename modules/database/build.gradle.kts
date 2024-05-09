plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.room")
    alias(libs.plugins.kotlin.serialization)

}

android {
    namespace = "com.mshdabiola.database"
}
room {
    schemaDirectory("$projectDir/schemas")
}