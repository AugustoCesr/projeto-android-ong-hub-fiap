package br.com.fiap.onghub.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class DicaVoluntariado(
    val titulo: String,
    val descricao: String,
    val icon: @Composable () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DicasDeVoluntarioScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val dicas = listOf(
        DicaVoluntariado(
            "Pesquise antes de começar",
            "Escolha uma causa que você realmente se identifique. Isso torna a experiência mais significativa.",
            { Icon(Icons.Default.Lightbulb, contentDescription = null, tint = MaterialTheme.colorScheme.primary) }
        ),
        DicaVoluntariado(
            "Seja comprometido",
            "Organizações contam com sua dedicação. Honre horários e responsabilidades assumidas.",
            { Icon(Icons.Default.Handshake, contentDescription = null, tint = MaterialTheme.colorScheme.primary) }
        ),
        DicaVoluntariado(
            "Trabalhe em equipe",
            "Voluntariado é colaboração. Escute, respeite e apoie outros voluntários.",
            { Icon(Icons.Default.People, contentDescription = null, tint = MaterialTheme.colorScheme.primary) }
        ),
        DicaVoluntariado(
            "Compartilhe conhecimento",
            "Use suas habilidades para somar: ensino, comunicação, tecnologia e muito mais podem ajudar.",
            { Icon(Icons.Default.Favorite, contentDescription = null, tint = MaterialTheme.colorScheme.primary) }
        ),
        DicaVoluntariado(
            "Pratique empatia",
            "Coloque-se no lugar das pessoas que você está ajudando. Isso faz toda a diferença.",
            { Icon(Icons.Default.VolunteerActivism, contentDescription = null, tint = MaterialTheme.colorScheme.primary) }
        ),
        DicaVoluntariado(
            "Informe-se sobre a governança",
            "Antes de se engajar, entenda como a ONG é administrada, sua transparência financeira e seriedade na gestão. Isso garante confiança e credibilidade.",
            { Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.primary) }
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dicas de Voluntariado") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(dicas) { dica ->
                ElevatedCard(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        dica.icon()
                        Column {
                            Text(dica.titulo, style = MaterialTheme.typography.titleMedium)
                            Text(
                                dica.descricao,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

