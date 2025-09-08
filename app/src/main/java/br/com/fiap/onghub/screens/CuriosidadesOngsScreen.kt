package br.com.fiap.onghub.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class CuriosidadeOng(
    val titulo: String,
    val descricao: String,
    val icon: @Composable () -> Unit,
    val gradient: List<Color>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CuriosidadesOngsScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val curiosidades = listOf(
        CuriosidadeOng(
            "Origem do termo ONG",
            "O termo “ONG” (Organização Não Governamental) foi oficializado pela ONU em 1945. Ele serve para diferenciar instituições independentes de governos, mas que ainda assim exercem forte influência em políticas públicas e ações sociais.",
            { Icon(Icons.Default.Info, contentDescription = null, tint = Color.White) },
            listOf(Color(0xFF6366F1), Color(0xFF818CF8)) // Roxo azulado
        ),
        CuriosidadeOng(
            "Maior ONG do mundo",
            "A BRAC, fundada em Bangladesh em 1972, é considerada a maior ONG do planeta. Atua em mais de 10 países com programas de microcrédito, educação, saúde e capacitação profissional, beneficiando milhões de pessoas todos os anos.",
            { Icon(Icons.Default.Star, contentDescription = null, tint = Color.White) },
            listOf(Color(0xFFF59E0B), Color(0xFFFCD34D)) // Amarelo dourado
        ),
        CuriosidadeOng(
            "Causas mais comuns no Brasil",
            "No Brasil, as ONGs se concentram principalmente em educação, assistência social e proteção animal. Muitas delas também surgem em comunidades carentes, onde o Estado tem pouca presença, para oferecer serviços básicos.",
            { Icon(Icons.Default.School, contentDescription = null, tint = Color.White) },
            listOf(Color(0xFF10B981), Color(0xFF34D399)) // Verde suave
        ),
        CuriosidadeOng(
            "Impacto global",
            "Estima-se que existam mais de 10 milhões de ONGs ativas no mundo, abrangendo desde pequenos grupos comunitários até grandes organizações globais. Elas desempenham papéis essenciais em emergências humanitárias e no combate à desigualdade.",
            { Icon(Icons.Default.Public, contentDescription = null, tint = Color.White) },
            listOf(Color(0xFF3B82F6), Color(0xFF60A5FA)) // Azul vivo
        ),
        CuriosidadeOng(
            "Força do voluntariado",
            "Em diversas ONGs, o voluntariado chega a representar a maior parte da força de trabalho. Além do impacto direto, ele ajuda a criar uma rede de apoio emocional e social que fortalece tanto quem ajuda quanto quem recebe a ajuda.",
            { Icon(Icons.Default.VolunteerActivism, contentDescription = null, tint = Color.White) },
            listOf(Color(0xFFEC4899), Color(0xFFF472B6)) // Rosa vibrante
        ),
        CuriosidadeOng(
            "ONGs e tecnologia",
            "Muitas ONGs já utilizam aplicativos, big data e inteligência artificial para otimizar recursos, monitorar projetos e aumentar a transparência. Isso aproxima doadores, voluntários e beneficiados em tempo real.",
            { Icon(Icons.Default.Devices, contentDescription = null, tint = Color.White) },
            listOf(Color(0xFF8B5CF6), Color(0xFFA78BFA)) // Roxo moderno
        ),
        CuriosidadeOng(
            "ONGs ambientais",
            "Organizações como Greenpeace e WWF são fundamentais na luta contra o desmatamento, poluição dos oceanos e mudanças climáticas. Além da defesa ambiental, elas também atuam em educação ecológica e políticas globais de sustentabilidade.",
            { Icon(Icons.Default.Forest, contentDescription = null, tint = Color.White) },
            listOf(Color(0xFF16A34A), Color(0xFF4ADE80)) // Verde floresta
        ),
        CuriosidadeOng(
            "ONGs e esportes",
            "Projetos sociais esportivos têm ganhado força como ferramenta de inclusão. ONGs oferecem atividades como futebol, capoeira e artes marciais para jovens em situação de vulnerabilidade, promovendo saúde, disciplina e oportunidades.",
            { Icon(Icons.Default.SportsSoccer, contentDescription = null, tint = Color.White) },
            listOf(Color(0xFF2563EB), Color(0xFF3B82F6)) // Azul esportivo
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Curiosidades sobre ONGs") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(curiosidades) { curiosidade ->
                ElevatedCard(
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                Brush.verticalGradient(colors = curiosidade.gradient),
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .background(Color.White.copy(alpha = 0.25f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                curiosidade.icon()
                            }
                            Column {
                                Text(
                                    curiosidade.titulo,
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = Color.White
                                )
                                Spacer(Modifier.height(6.dp))
                                Text(
                                    curiosidade.descricao,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White.copy(alpha = 0.95f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
