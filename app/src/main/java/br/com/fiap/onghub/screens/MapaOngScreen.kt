package br.com.fiap.onghub.screens

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import org.maplibre.android.MapLibre
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.Style
import org.maplibre.android.style.layers.CircleLayer
import org.maplibre.android.style.layers.PropertyFactory.*
import org.maplibre.android.style.sources.GeoJsonSource
import org.maplibre.android.utils.ColorUtils
import org.maplibre.geojson.Feature
import org.maplibre.geojson.Point

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

    val vm: MapaOngViewModel = viewModel(
        factory = viewModelFactory {
            initializer { MapaOngViewModel(address, latArg, lngArg) }
        }
    )

    val coords = vm.coords
    val isGeocoding = vm.isGeocoding
    val geocodeError = vm.geocodeError

    LaunchedEffect(Unit) { MapLibre.getInstance(context) }

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
        }
    ) { inner ->
        Box(Modifier.fillMaxSize().padding(inner)) {
            when {
                coords == null && isGeocoding -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
                coords == null && !isGeocoding -> {
                    Text(
                        text = geocodeError ?: "Endereço não informado.",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    if (!address.isNullOrBlank()) {
                        OutlinedButton(
                            onClick = {
                                val uri =
                                    "https://www.google.com/maps/search/?api=1&query=${Uri.encode(address)}"
                                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
                            },
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(16.dp)
                        ) { Text("Abrir no Google Maps") }
                    }
                }
                coords != null -> {
                    val (lat, lng) = coords
                    val lifecycle = LocalLifecycleOwner.current.lifecycle

                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = { ctx ->
                            val mv = MapView(ctx)
                            mv.onCreate(null)
                            lifecycle.addObserver(object : DefaultLifecycleObserver {
                                override fun onStart(owner: LifecycleOwner) = mv.onStart()
                                override fun onResume(owner: LifecycleOwner) = mv.onResume()
                                override fun onPause(owner: LifecycleOwner) = mv.onPause()
                                override fun onStop(owner: LifecycleOwner) = mv.onStop()
                                override fun onDestroy(owner: LifecycleOwner) = mv.onDestroy()
                            })

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
}
