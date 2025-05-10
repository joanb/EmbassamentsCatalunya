package com.joandev.embassamentscatalunya.data

import com.joandev.embassamentscatalunya.network.httpClientEngineFactory
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// Expect actual for HttpClient engine factory
// This allows platform-specific engines (Android, iOS-Darwin, JVM-CIO)

class ReservoirService {
    private val httpClient =
        HttpClient(httpClientEngineFactory()) { // Use the platform-specific engine
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }

    private val dataUrl = "https://analisi.transparenciacatalunya.cat/resource/gn9e-3qhr.json"

    suspend fun getReservoirData(): List<ReservoirApiModel> {
        return try {
            httpClient.get(dataUrl).body<List<ReservoirApiModel>>()
        } catch (e: Exception) {
            println("Error fetching reservoir data: ${e.message}") // Proper logging recommended
            emptyList()
        }
    }

    fun close() {
        httpClient.close()
    }
}