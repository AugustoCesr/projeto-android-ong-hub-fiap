package br.com.fiap.onghub.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Pix
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.VolunteerActivism
import androidx.compose.material.icons.rounded.Whatsapp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.fiap.onghub.ui.home.DetalheUiState
import br.com.fiap.onghub.ui.home.DetalheViewModel
import coil.compose.AsyncImage
import androidx.core.net.toUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalhesOrganizacoesScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    vm: DetalheViewModel = viewModel(
        factory = DetalheViewModel.Factory
    )
) {
    fun categoryFromLabel(label: String): Category? {
        return Category.entries.find { it.label.equals(label, ignoreCase = true) }
    }

    when (val state = vm.uiState) {
        DetalheUiState.Loading -> Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        DetalheUiState.Empty -> Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Detalhes") },
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.popBackStack() }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Voltar"
                            )
                        }
                    }
                )
            }
        ) { inner -> Box(Modifier.padding(inner), contentAlignment = Alignment.Center) { Text("ONG não encontrada") } }

        is DetalheUiState.Error -> Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Detalhes") },
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.popBackStack() }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Voltar"
                            )
                        }
                    }
                )
            }
        ) { inner ->
            Column(Modifier.padding(inner).padding(16.dp)) {
                Text("Ops, algo deu errado", color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(8.dp))
                Text(state.message, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(12.dp))
                OutlinedButton(onClick = vm::load) { Text("Tentar novamente") }
            }
        }

        is DetalheUiState.Success -> {
            val ong = state.ong
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(ong.name, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                        navigationIcon = {
                            IconButton(
                                onClick = { navController.popBackStack() }
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Voltar"
                                )
                            }
                        }
                    )
                }
            ) { inner ->
                LazyColumn(
                    modifier = modifier.padding(inner),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        AsyncImage(
                            model = ong.card_image ?: ong.images?.firstOrNull(),
                            contentDescription = ong.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 9f)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(ong.name, style = MaterialTheme.typography.headlineSmall)

                            val loc = listOfNotNull(ong.address?.city, ong.address?.state)
                                .filter { it.isNotBlank() }.joinToString(" - ")
                            if (loc.isNotBlank()) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        Icons.Rounded.Place,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        loc,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }

                            val badges = ong.categories.orEmpty().distinct()
                            if (badges.isNotEmpty()) {
                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    badges.forEach { CategoryBadge(categoryFromLabel(it)) }
                                }
                            }

                            if (!ong.description.isNullOrBlank()) {
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    ong.description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    item { Spacer(Modifier.height(3.dp)) }

                    item {
                        ElevatedCard {
                            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text("Como ajudar", style = MaterialTheme.typography.titleMedium)
                                    Spacer(Modifier.height(3.dp))
                                }

                                if (!ong.pix_key.isNullOrBlank()) {
                                    InfoLine(Icons.Rounded.Pix, "Chave PIX: ${ong.pix_key}")
                                }

                                val openLink = rememberOpenLink()
                                if (!ong.whatsapp_link.isNullOrBlank()) {
                                    InfoLine(Icons.Rounded.Whatsapp, "WhatsApp: ${ong.whatsapp_link}") {
                                        openLink(ong.whatsapp_link)
                                    }
                                }

                                if (!ong.website.isNullOrBlank()) {
                                    InfoLine(Icons.Rounded.Public, "Site: ${ong.website}") {
                                        openLink(ong.website)
                                    }
                                }

                                if (!ong.instagram.isNullOrBlank()) {
                                    InfoLine(Icons.Rounded.Link, "Instagram: ${ong.instagram}") {
                                        openLink(ong.instagram)
                                    }
                                }
                            }
                        }
                    }

                    item {
                        ElevatedCard {
                            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Text("Contato & Endereço", style = MaterialTheme.typography.titleMedium)

                                if (!ong.phone.isNullOrBlank()) {
                                    InfoLine(Icons.Rounded.Phone, "Telefone: ${ong.phone}")
                                }
                                if (!ong.email.isNullOrBlank()) {
                                    InfoLine(Icons.Rounded.Email, "E-mail: ${ong.email}")
                                }

                                val addr = ong.address
                                val addressLine = listOfNotNull(
                                    addr?.street_name?.takeIf { it.isNotBlank() }?.let { name ->
                                val num = addr.street_number ?: ""
                                "$name $num".trim()
                            },
                            addr?.neighborhood?.takeIf { it.isNotBlank() },
                            listOfNotNull(
                                addr?.city,
                                addr?.state
                            ).filter { it.isNotBlank() }.joinToString(" - ").takeIf { it.isNotBlank() },
                            addr?.cep?.takeIf { it.isNotBlank() }
                            ).joinToString(", ")

                                if (addressLine.isNotBlank()) {
                                    InfoLine(Icons.Rounded.Place, addressLine)
                                }
                            }
                        }
                    }
                    item { Spacer(Modifier.height(8.dp)) }
                }
            }
        }
    }
}

@Composable
private fun CategoryBadge(cat: Category?) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(cat?.color?.copy(alpha = 0.18f) ?: Color.Gray)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(cat?.label ?: "Categoria", color = cat?.color ?: Color.Gray, style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
private fun InfoLine(
    icon: ImageVector,
    text: String,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Text(text, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun rememberOpenLink(): (String) -> Unit {
    val context = LocalContext.current
    return { url ->
        runCatching {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            context.startActivity(intent)
        }
    }
}
