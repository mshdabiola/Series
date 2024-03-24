package com.mshdabiola.data.repository.inter

import dev.shreyaspatil.ai.client.generativeai.type.GenerateContentResponse
import dev.shreyaspatil.ai.client.generativeai.type.PlatformImage
import dev.shreyaspatil.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.Flow

interface IGenerative {

    fun generateContent(prompt: String): Flow<GenerateContentResponse>

    fun generateContent(prompt: String, imageData: ByteArray): Flow<GenerateContentResponse>
    suspend fun generateContent2(prompt: String, imageData: ByteArray): GenerateContentResponse
}