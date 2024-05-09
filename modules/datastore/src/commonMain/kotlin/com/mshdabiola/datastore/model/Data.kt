package com.mshdabiola.datastore.model

import androidx.datastore.core.okio.OkioSerializer
import com.mshdabiola.model.Contrast
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource

val json = Json

internal object QuestionJsonSerializer : OkioSerializer<Map<Long, QuestionSer>> {

    override val defaultValue: Map<Long, QuestionSer>
        get() = mapOf()

    override suspend fun readFrom(source: BufferedSource): Map<Long, QuestionSer> {
        return json.decodeFromString<Map<Long, QuestionSer>>(source.readUtf8())
    }

    override suspend fun writeTo(t: Map<Long, QuestionSer>, sink: BufferedSink) {
        sink.use {
            it.writeUtf8(
                json.encodeToString(
                    MapSerializer(
                        Long.serializer(),
                        QuestionSer.serializer()
                    ), t
                )
            )
        }
    }
}


internal object InstructionJsonSerializer : OkioSerializer<Map<Long, InstructionSer>> {

    override val defaultValue: Map<Long, InstructionSer>
        get() = mapOf()

    override suspend fun readFrom(source: BufferedSource): Map<Long, InstructionSer> {
        return json.decodeFromString<Map<Long, InstructionSer>>(source.readUtf8())
    }

    override suspend fun writeTo(t: Map<Long, InstructionSer>, sink: BufferedSink) {
        sink.use {
            it.writeUtf8(
                json.encodeToString(
                    MapSerializer(
                        Long.serializer(),
                        InstructionSer.serializer()
                    ), t
                )
            )
        }
    }
}


internal object CurrentExamJsonSerializer : OkioSerializer<CurrentExamSer> {

    override val defaultValue: CurrentExamSer
        get() = CurrentExamSer(id = -1, choose = listOf())

    override suspend fun readFrom(source: BufferedSource): CurrentExamSer {
        return json.decodeFromString<CurrentExamSer>(source.readUtf8())
    }

    override suspend fun writeTo(t: CurrentExamSer, sink: BufferedSink) {
        sink.use {
            it.writeUtf8(json.encodeToString(CurrentExamSer.serializer(), t))
        }
    }
}


internal object UserDataJsonSerializer : OkioSerializer<UserDataSer> {

    override val defaultValue: UserDataSer
        get() = UserDataSer(
            themeBrand = ThemeBrand.DEFAULT,
            darkThemeConfig = DarkThemeConfig.LIGHT,
            useDynamicColor = false,
            shouldHideOnboarding = false,
            contrast = Contrast.Normal,
        )

    override suspend fun readFrom(source: BufferedSource): UserDataSer {
        return json.decodeFromString<UserDataSer>(source.readUtf8())
    }

    override suspend fun writeTo(t: UserDataSer, sink: BufferedSink) {
        sink.use {
            it.writeUtf8(json.encodeToString(UserDataSer.serializer(), t))
        }
    }
}
