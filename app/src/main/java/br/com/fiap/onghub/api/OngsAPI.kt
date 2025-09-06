package br.com.fiap.onghub.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface OngsApi {
    @GET("api/ongs")
    suspend fun getOngsRaw(
        @Query("name") name: String? = null,
        @Query("city") city: String? = null,
        @Query("state") state: String? = null,
        @Query("category") category: String? = null,
        @Query("page") page: Int? = 1,
        @Query("limit") limit: Int? = 10
    ): okhttp3.ResponseBody
}

object ApiModule {
    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    private val rateLimit = Interceptor { chain ->
        synchronized(this) {
            Thread.sleep(1100)
            chain.proceed(chain.request())
        }
    }

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(rateLimit)
        .addNetworkInterceptor(logging)
        .build()

    val api: OngsApi = Retrofit.Builder()
        .baseUrl("https://ongs-brasil.org/")
        .client(client)
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .build()
        .create(OngsApi::class.java)
}

