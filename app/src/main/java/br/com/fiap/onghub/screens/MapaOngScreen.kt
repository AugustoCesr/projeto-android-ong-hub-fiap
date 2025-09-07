package br.com.fiap.onghub.screens

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.maplibre.android.MapLibre
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.Style
import org.maplibre.android.style.layers.CircleLayer
import org.maplibre.android.style.layers.PropertyFactory.*
import org.maplibre.android.style.sources.GeoJsonSource
import org.maplibre.geojson.Feature
import org.maplibre.geojson.Point
import org.maplibre.android.utils.ColorUtils
import android.graphics.Color
import androidx.compose.ui.unit.dp
import java.net.HttpURLConnection
import java.net.URLEncoder
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapaOngScreen(
    navController: NavController,
    name: String,
    address: String?,
    latArg: Double?,
    lngArg: Double?
) {
    val context = LocalContext.current
    val snackbar = remember { SnackbarHostState() }

    LaunchedEffect(Unit) { MapLibre.getInstance(context) }

    var coords by remember {
        mutableStateOf<Pair<Double, Double>?>(if (latArg != null && lngArg != null) latArg to lngArg else null)
    }
    var isGeocoding by remember { mutableStateOf(false) }
    var geocodeError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(address) {
        if (coords == null && !address.isNullOrBlank()) {
            isGeocoding = true
            geocodeError = null
            coords = try {
                geocodeWithNominatim(address)
            } catch (e: Exception) {
                geocodeError = "Não foi possível localizar o endereço."
                null
            } finally {
                isGeocoding = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(name.ifBlank { "Localização" }) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbar) }
    ) { inner ->
        Box(Modifier.fillMaxSize().padding(inner)) {
            if (coords == null && isGeocoding) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            } else if (coords == null && !isGeocoding) {
                Text(
                    text = geocodeError ?: "Endereço não informado.",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
                if (!address.isNullOrBlank()) {
                    OutlinedButton(
                        onClick = {
                            val uri = "https://www.google.com/maps/search/?api=1&query=${Uri.encode(address)}"
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                    ) { Text("Abrir no Google Maps") }
                }
            } else {
                val (lat, lng) = coords!!
                val lifecycle = LocalLifecycleOwner.current.lifecycle

                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { ctx ->
                        val mv = MapView(ctx)
                        mv.onCreate(null)
                        lifecycle.addObserver(object : DefaultLifecycleObserver {
                            override fun onStart(owner: LifecycleOwner) { mv.onStart() }
                            override fun onResume(owner: LifecycleOwner) { mv.onResume() }
                            override fun onPause(owner: LifecycleOwner) { mv.onPause() }
                            override fun onStop(owner: LifecycleOwner) { mv.onStop() }
                            override fun onDestroy(owner: LifecycleOwner) { mv.onDestroy() }
                        })

                        mv.getMapAsync { map ->
                            mv.getMapAsync { map ->
                                map.setStyle(
                                    Style.Builder().fromUri("https://basemaps.cartocdn.com/gl/positron-gl-style/style.json")
                                ) { style ->
                                    val source = GeoJsonSource(
                                        "ong-source",
                                        Feature.fromGeometry(Point.fromLngLat(lng, lat))
                                    )
                                    style.addSource(source)

                                    val layer = CircleLayer("ong-circle", "ong-source").withProperties(
                                        circleColor(ColorUtils.colorToRgbaString(Color.RED)),
                                        circleRadius(8f),
                                        circleStrokeColor(ColorUtils.colorToRgbaString(Color.WHITE)),
                                        circleStrokeWidth(2f)
                                    )
                                    style.addLayer(layer)

                                    map.cameraPosition = CameraPosition.Builder()
                                        .target(LatLng(lat, lng))
                                        .zoom(16.0)
                                        .build()
                                }

                            }
                        }
                        mv
                    }
                )

                OutlinedButton(
                    onClick = {
                        val uri = "geo:$lat,$lng?q=${Uri.encode("$lat,$lng ($name)")} "
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                ) { Text("Abrir no Google Maps") }
            }
        }
    }
}

private suspend fun geocodeWithNominatim(query: String): Pair<Double, Double>? = withContext(Dispatchers.IO) {
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
