package br.com.fiap.onghub.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.onghub.R
import br.com.fiap.onghub.components.ContatoItem
import br.com.fiap.onghub.components.Header
import br.com.fiap.onghub.ui.theme.roboto

@Composable
fun DetalhesOrganizacoesScreen(modifier: Modifier = Modifier, navController: NavController) {

    var listaDeRequisitos = listOf<String>(
        "Ser maior de idade (18 anos ou mais).",
        "Ter disponibilidade mínima de 30 dias para participação.",
        "Estudantes de Biologia, Oceanografia, Medicina Veterinária, " +
                "Engenharia Ambiental ou Turismo têm prioridade nos estágios-voluntários.",
        "Apresentar documentação exigida: currículo, carta de motivação e, em alguns casos, comprovantes de vacinação."
    )
    val context = LocalContext.current

    Box(modifier = modifier) {
        Column() {
            Header(
                titulo = "Projeto Tamar",
                onButtonClick = {
                    navController.navigate("organizacoes")
                }
            )
            Column(modifier =
                Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Card(elevation = CardDefaults.cardElevation(4.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.projeto_tamar_banner),
                        contentDescription = "Imagem de duas pessoas do Projeto Tamar cuidando de uma tartaruga"
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier =
                        Modifier
                            .padding(horizontal = 16.dp, vertical = 32.dp)) {
                        Row(verticalAlignment = Alignment.Top) {
                            Image(
                                painter = painterResource(id = R.drawable.projeto_tamar),
                                contentDescription = "Logo do Projeto Tamar",
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(shape = RoundedCornerShape(8.dp))
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column() {
                                Text(
                                    text = "Projeto Tamar",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = roboto
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.plantinha),
                                        contentDescription = "Imagem de uma plantinha com fundo verde",
                                        modifier = Modifier
                                            .size(33.dp)
                                            .clip(shape = CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Meio Ambiente & Bem-Estar Animal",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = roboto
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = "Ícone de localização",
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Praia do Forte, BA",
                                        fontSize = 13.sp,
                                        fontFamily = roboto
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedButton(
                            onClick = { },
                            modifier = Modifier.fillMaxWidth(),
                            border = BorderStroke(1.dp, colorResource(id = R.color.azul_padrao)),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = colorResource(id = R.color.azul_padrao)
                            ),
                            elevation = ButtonDefaults.buttonElevation(3.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Ícone de localização",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Ver no Mapa",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = roboto
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.profile),
                                contentDescription = "Ícone de folha de perfil"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Sobre a Organização",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = roboto
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "O Projeto Tamar é uma das organizações mais respeitadas do Brasil " +
                                "na área de conservação marinha. Há mais de 40 anos, trabalha incansavelmente para " +
                                "proteger as tartarugas marinhas e seus habitats, desenvolvendo programas de pesquisa, " +
                                "conservação e educação ambiental.",
                            fontSize = 14.sp,
                            fontFamily = roboto
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 20.dp),
                            thickness = 1.dp,
                            color = Color.LightGray
                        )
                        Text(
                            text = "Objetivo",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = roboto
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Conservar as espécies de tartarugas marinhas que ocorrem no Brasil através da " +
                                    "proteção de seus habitats e da conscientização da sociedade.",
                            fontSize = 14.sp,
                            fontFamily = roboto
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.help),
                                contentDescription = "Ícone de ajuda"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Como Ajudar",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = roboto
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Você pode contribuir com o Projeto Tamar de duas formas principais: " +
                                    "através do voluntariado ou por meio de doações.\n" +
                                    "Quem deseja se voluntariar encontra oportunidades em diferentes bases pelo Brasil, " +
                                    "como Vitória, Guriri, Regência, Fernando de Noronha e Ubatuba. As atividades envolvem " +
                                    "apoio em pesquisa científica, monitoramento de praias, educação ambiental e reabilitação " +
                                    "de animais marinhos. Em Ubatuba (SP), existe um programa de estágio-voluntário para " +
                                    "estudantes de áreas como Biologia, Oceanografia, Medicina Veterinária, Engenharia Ambiental e " +
                                    "Turismo, que inclui atividades de campo e capacitação. Já em Florianópolis (SC), em parceria " +
                                    "com a plataforma Worldpackers, é possível participar de ações de soltura de tartarugas, " +
                                    "monitoramento e programas de educação ambiental, contando com hospedagem durante o período " +
                                    "de voluntariado.\n" +
                                    "Outra forma de ajudar é através de doações. Como organização sem fins lucrativos, o " +
                                    "Projeto Tamar depende do apoio da sociedade para manter suas atividades. As contribuições " +
                                    "financeiras, a compra de produtos oficiais e a visita às bases de conservação garantem a " +
                                    "continuidade dos programas de preservação e educação ambiental.",
                            fontSize = 14.sp,
                            fontFamily = roboto
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 20.dp),
                            thickness = 1.dp,
                            color = Color.LightGray
                        )
                        Text(
                            text = "Requisitos",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = roboto
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        for (requisito in listaDeRequisitos) {
                            Row(verticalAlignment = Alignment.Top) {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = "Ícone de certo como requisito",
                                    tint = colorResource(id = R.color.azul_padrao)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "$requisito",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = roboto
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gov.br/" +
                                        "icmbio/pt-br/assuntos/centros-de-pesquisa/tartarugas-marinhas-e-biodiversidade-marinha-do-leste" +
                                        "/Arquivos%20do%20Site/imagens%20e%20videos/voluntariado-no-centro-tamar-icmbio" +
                                        "/voluntariado-no-centro-tamar-icmbio"))
                                context.startActivity(intent)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.azul_padrao),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp),
                            elevation = ButtonDefaults.buttonElevation(3.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.front_hand),
                                    contentDescription = "Ícone de uma mão para frente"
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "Quero ser voluntário",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = roboto
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(Color(0xFFFFF8E1))
                        ) {
                            Row(
                                verticalAlignment = Alignment.Top,
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = "Ícone de aviso",
                                    tint = Color(0xFFF57C00),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    buildAnnotatedString {
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append("Importante: ")
                                        }
                                        append("Antes de fazer doações ou se voluntariar, verifique a " +
                                                "transparência e reputação da organização.")
                                    },
                                    color = Color(0xFF6D4C41),
                                    fontSize = 14.sp,
                                    fontFamily = roboto
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 32.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.contact_mail),
                                contentDescription = "Ícone de contato"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Contato",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = roboto
                            )
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        ContatoItem(
                            icon = painterResource(id = R.drawable.call),
                            contentDescriptionImage = "Ícone de telefone",
                            iconTint = Color(0xFF135313),
                            backgroundColor = Color.Green,
                            text = "+55 (71) 98127-2010"
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        ContatoItem(
                            icon = painterResource(id = R.drawable.mark_email_read),
                            contentDescriptionImage = "Ícone de email",
                            iconTint = Color(0xFF11116D),
                            backgroundColor = Color.Blue,
                            text = "tamar@tamar.org.br"
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            ContatoItem(
                                icon = painterResource(id = R.drawable.language),
                                contentDescriptionImage = "Ícone do site",
                                iconTint = Color(0xFF48125F),
                                backgroundColor = Color.Magenta,
                                text = "Site oficial"
                            )
                            OutlinedButton(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tamar.org.br/"))
                                    context.startActivity(intent)
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = colorResource(id = R.color.azul_padrao),
                                    containerColor = Color.White
                                ),
                                border = BorderStroke(1.dp, colorResource(id = R.color.azul_padrao)),
                                elevation = ButtonDefaults.buttonElevation(1.dp)
                            ) {
                                Text(
                                    text = "Visitar",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = roboto
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE8F5E9)
                    )
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.verified_user),
                                contentDescription = "Ícone de verificado",
                                tint = Color(0xFF2E7D32),
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Organização Verificada",
                                color = Color(0xFF2E7D32),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = roboto
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Esta ONG foi verificada e possui informações confiáveis sobre suas atividades.",
                            color = Color(0xFF1B5E20),
                            fontSize = 14.sp,
                            fontFamily = roboto,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}