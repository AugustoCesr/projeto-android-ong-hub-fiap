package br.com.fiap.onghub.api

import br.com.fiap.onghub.api.response.ONG
import br.com.fiap.onghub.api.response.OngApiResponse
import br.com.fiap.onghub.screens.Category
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

class OngsRepository(
    private val api: OngsApi = ApiModule.api
) {
    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun fetchOngs(
        city: String?,
        state: String?,
        category: String?,
        page: Int,
        limit: Int
    ): OngApiResponse {
        val body = api.getOngsRaw(
            city = city?.ifBlank { null },
            state = state?.ifBlank { null },
            category = category?.ifBlank { null },
            page = page,
            limit = limit
        )
        body.use { rb ->
            rb.byteStream().use { stream ->
                return json.decodeFromStream<OngApiResponse>(stream)
            }
        }
    }

    suspend fun fetchOngById(id: String): ONG? {
        val body = api.getOngsRaw(id = id, limit = 1)
        body.use { rb ->
            rb.byteStream().use { stream ->
                val resp = json.decodeFromStream<OngApiResponse>(stream)
                return resp.data.firstOrNull()
            }
        }
    }
    fun categories(): List<Category> = Category.entries
}
