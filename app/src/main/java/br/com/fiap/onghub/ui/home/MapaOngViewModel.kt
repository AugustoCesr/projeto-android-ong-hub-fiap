package br.com.fiap.onghub.screens

import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class MapaOngViewModel(
    private val address: String?,
    private val latArg: Double?,
    private val lngArg: Double?
) : ViewModel() {

    var coords by mutableStateOf<Pair<Double, Double>?>(null)
        private set

    var isGeocoding by mutableStateOf(false)
        private set

    var geocodeError by mutableStateOf<String?>(null)
        private set

    init {
        if (latArg != null && lngArg != null) {
            coords = latArg to lngArg
        } else if (!address.isNullOrBlank()) {
            geocode(address)
        }
    }

    private fun geocode(query: String) {
        viewModelScope.launch {
            isGeocoding = true
            geocodeError = null
            try {
                coords = geocodeWithNominatim(query)
                if (coords == null) geocodeError = "Não foi possível localizar o endereço."
            } catch (e: Exception) {
                geocodeError = "Erro ao localizar endereço."
            } finally {
                isGeocoding = false
            }
        }
    }
}

    private suspend fun geocodeWithNominatim(query: String): Pair<Double, Double>? =
        withContext(Dispatchers.IO) {
            val url = URL(
                "https://nominatim.openstreetmap.org/search?format=json&q=${
                    URLEncoder.encode(query, "UTF-8")
                }&limit=1"
            )
            (url.openConnection() as HttpURLConnection).run {
                requestMethod = "GET"
                connectTimeout = 8000
                readTimeout = 8000
                setRequestProperty(
                    "User-Agent",
                    "ONGHub/1.0 (${Build.MANUFACTURER} ${Build.MODEL})"
                )
                inputStream.bufferedReader().use { reader ->
                    val arr = JSONArray(reader.readText())
                    if (arr.length() > 0) {
                        val obj = arr.getJSONObject(0)
                        val lat = obj.getString("lat").toDoubleOrNull()
                        val lon = obj.getString("lon").toDoubleOrNull()
                        if (lat != null && lon != null) lat to lon else null
                    } else null
                }
            }
        }

