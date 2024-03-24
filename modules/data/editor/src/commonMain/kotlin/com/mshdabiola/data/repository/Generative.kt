package com.mshdabiola.data.repository

import com.mshdabiola.data.repository.inter.IGenerative
import dev.shreyaspatil.ai.client.generativeai.GenerativeModel
import dev.shreyaspatil.ai.client.generativeai.type.GenerateContentResponse
import dev.shreyaspatil.ai.client.generativeai.type.PlatformImage
import dev.shreyaspatil.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.Flow

class Generative: IGenerative {
    private val apiKey="AIzaSyAjYhexe9sqDPQ0SX-2G-S10wr9CkNBKIg"
    private val generativeVisionModel = GenerativeModel(
        modelName = "gemini-pro-vision",
        apiKey = apiKey
    )

    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = apiKey
    )

    override fun generateContent(prompt: String): Flow<GenerateContentResponse> {
        return generativeModel.generateContentStream(prompt)
    }

    override fun generateContent(prompt: String, imageData: ByteArray): Flow<GenerateContentResponse> {
        val content = content {
            image(PlatformImage(imageData))
            text(prompt)
        }
        return generativeVisionModel.generateContentStream(content)
    }

    override suspend fun generateContent2(prompt: String, imageData: ByteArray): GenerateContentResponse {
        val content = content {
            image(PlatformImage(imageData))
            text(prompt)
        }
        return generativeVisionModel.generateContent(content)//.generateContentStream(content)
    }
}