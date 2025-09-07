package br.com.fiap.onghub.api.response


import br.com.fiap.onghub.screens.Category
import kotlinx.serialization.Serializable

@Serializable
data class OngApiResponse(
    val success: Boolean,
    val data: List<ONG>,
    val pagination: Pagination? = null
)

@Serializable
data class ONG(
    val id: String,
    val name: String,
    val website: String? = null,
    val description: String? = null,
    val resume: String? = null,
    val card_image: String? = null,
    val phone_number: String? = null,
    val whatsapp_number: String? = null,
    val instagram: String? = null,
    val whatsapp_link: String? = null,
    val pix_key: String? = null,
    val email: String? = null,
    val cnpj: String? = null,
    val images: List<String>? = null,
    val address: Address? = null,
    val phone: String? = null,
    val founded_year: Int? = null,
    val categories: List<String>? = null
)

@Serializable
data class Address(
    val street_name: String? = null,
    val street_number: Int? = null,
    val neighborhood: String? = null,
    val city: String? = null,
    val state: String? = null,
    val location: String? = null,
    val cep: String? = null,
    val google_maps_embed_url: String? = null
)

@Serializable
data class Pagination(
    val current_page: Int,
    val per_page: Int,
    val total_results: Int,
    val total_pages: Int,
    val has_next_page: Boolean,
    val next_page: Int? = null
)