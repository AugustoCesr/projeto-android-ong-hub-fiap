package br.com.fiap.onghub.screens

import androidx.annotation.ColorLong
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import br.com.fiap.onghub.R
import br.com.fiap.onghub.components.HeaderOng
import br.com.fiap.onghub.ui.home.HomeViewModel
import coil.compose.AsyncImage

enum class Category(val label: String, @ColorLong val colorHex: Long) {
    EDUCATION("Educação", 0xFF3B82F6),
    ANIMALS("Animais", 0xFFEC4899),
    CHILDREN("Crianças", 0xFF8B5CF6),
    ELDERLY("Idosos", 0xFFF59E0B),
    SUSTAINABILITY("Sustentabilidade", 0xFF16A34A)
}

val Category.color: Color
    get() = Color(colorHex)

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    contentPadding: PaddingValues = PaddingValues(),
    vm: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val ui by vm.ui.collectAsStateWithLifecycle()
    val fm = androidx.compose.ui.platform.LocalFocusManager.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp + contentPadding.calculateTopPadding(),
                bottom = contentPadding.calculateBottomPadding()
            ),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        HeaderOng()

        OutlinedTextField(
            value = ui.addressInput,
            onValueChange = vm::onAddressChange,
            label = { Text("Cidade - Estado") },
            placeholder = { Text("Ex.: São Bernardo do Campo - São Paulo") },
            leadingIcon = { Icon(Icons.Outlined.Search, null) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                fm.clearFocus()
                vm.applyAddress()
            }) { Text("Buscar") }
            OutlinedButton(onClick = {
                vm.onAddressChange("")
                vm.applyAddress()
            }) { Text("Limpar") }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(
                    onClick = {
                        navController.navigate("dicasVoluntariado")
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = colorResource(id = R.color.azul_padrao)
                    ),
                    border = BorderStroke(1.dp, colorResource(id = R.color.azul_padrao))
                ) {
                    Text(text = "Dicas Voluntariado")
                }
            }
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray
        )

        if (ui.categories.isNotEmpty()) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                ui.categories.forEach { cat ->
                    val selected = ui.selectedCategory == cat
                    FilterChip(
                        selected = selected,
                        onClick = { vm.toggleCategory(cat) },
                        shape = CircleShape,
                        label = { Text(cat.label) },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = cat.color.copy(alpha = 0.16f),
                            labelColor = cat.color,
                            iconColor = cat.color,
                            selectedContainerColor = cat.color,
                            selectedLabelColor = Color.White,
                            selectedLeadingIconColor = Color.White
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = cat.color.copy(alpha = 0.6f),
                            selectedBorderColor = cat.color,
                            enabled = true,
                            selected = selected,
                        )
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 12.dp)
        ) {
            items(ui.items) { ong ->
                ElevatedCard(onClick = { navController.navigate("detalhesOrganizacoes/${ong.id}") }) {
                    Column(Modifier.fillMaxWidth().padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(
                                model = ong.card_image ?: ong.images?.firstOrNull(),
                                contentDescription = ong.name,
                                modifier = Modifier.size(64.dp),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(Modifier.width(12.dp))
                            Column(Modifier.weight(1f)) {
                                Text(ong.name, style = MaterialTheme.typography.titleMedium)
                                val loc = listOfNotNull(ong.address?.city, ong.address?.state)
                                    .filter { it.isNotBlank() }.joinToString(" - ")
                                if (loc.isNotBlank()) {
                                    Text(
                                        loc,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                        val desc = ong.description.orEmpty()
                        if (desc.isNotBlank()) {
                            Spacer(Modifier.height(8.dp))
                            Text(desc, maxLines = 3, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }

            item {
                when {
                    ui.isLoading -> LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                    ui.error != null -> Text("Não encontrado", color = MaterialTheme.colorScheme.error)
                    ui.hasMore -> LaunchedEffect(ui.page) { vm.loadNext() }
                }
            }
        }
    }
}
