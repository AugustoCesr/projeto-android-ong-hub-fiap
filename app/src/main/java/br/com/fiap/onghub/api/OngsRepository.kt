package br.com.fiap.onghub.api

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
    fun categories(): List<Category> = Category.entries
}
