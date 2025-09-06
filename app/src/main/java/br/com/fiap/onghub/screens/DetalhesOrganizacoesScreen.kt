package br.com.fiap.onghub.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.fiap.onghub.ui.home.DetalheUiState
import br.com.fiap.onghub.ui.home.DetalheViewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalhesOrganizacoesScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    vm: DetalheViewModel = viewModel(
        factory = DetalheViewModel.Factory
    )
) {

    when (val st = vm.uiState) {
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
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
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
                Text(st.message, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(12.dp))
                OutlinedButton(onClick = vm::load) { Text("Tentar novamente") }
            }
        }

        is DetalheUiState.Success -> {
            val ong = st.ong
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
                                .aspectRatio(16f/9f)
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
                                Text(loc, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
                            }
                            if (!ong.description.isNullOrBlank()) {
                                Text(ong.description, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                    item {
                        ElevatedCard {
                            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text("Como ajudar", style = MaterialTheme.typography.titleMedium)
                                if (!ong.pix_key.isNullOrBlank()) Text("Chave PIX: ${ong.pix_key}")
                                if (!ong.whatsapp_link.isNullOrBlank()) Text("WhatsApp: ${ong.whatsapp_link}")
                                if (!ong.website.isNullOrBlank()) Text("Site: ${ong.website}")
                                if (!ong.instagram.isNullOrBlank()) Text("Instagram: ${ong.instagram}")
                                if (!ong.email.isNullOrBlank()) Text("E-mail: ${ong.email}")
                                if (!ong.phone.isNullOrBlank()) Text("Telefone: ${ong.phone}")
                            }
                        }
                    }
                    item {
                        ElevatedCard {
                            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text("Endereço", style = MaterialTheme.typography.titleMedium)
                                val addr = ong.address
                                Text(listOfNotNull(
                                    addr?.street_name?.let { "$it ${addr.street_number}" }.takeUnless { it.isNullOrBlank() },
                                    addr?.neighborhood,
                                    listOfNotNull(addr?.city, addr?.state).filter { !it.isNullOrBlank() }.joinToString(" - "),
                                    addr?.cep
                                ).filter { !it.isNullOrBlank() }.joinToString(", "))
                            }
                        }
                    }
                    item { Spacer(Modifier.height(8.dp)) }
                }
            }
        }
    }
}

