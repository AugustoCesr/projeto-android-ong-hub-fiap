package br.com.fiap.onghub.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.onghub.R
import br.com.fiap.onghub.components.Header
import br.com.fiap.onghub.components.OngCard
import br.com.fiap.onghub.ui.theme.roboto

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Column() {
            Header()
            Column(modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 32.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Encontre a sua causa",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = roboto,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Descubra organizações que fazem a diferença e saiba como contrubuir para um mundo melhor",
                    fontSize = 14.sp,
                    fontFamily = roboto,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Categorias de Causas",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = roboto,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                OngCard(
                    imagem = R.drawable.plantinha,
                    imagemDescricao = "Imagem de uma plantinha com fundo verde",
                    titulo = "Meio Ambiente & Bem-Estar Animal",
                    descricao = "Organizações dedicadas à preservação ambiental, proteção animal e sustentabilidade",
                    textoBotao = "Explorar ONGs  >",
                    onButtonClick = {}
                )
                Spacer(modifier = Modifier.height(20.dp))
                OngCard(
                    imagem = R.drawable.livros,
                    imagemDescricao = "Imagem de livros com fundo azul",
                    titulo = "Educação & Desenvolvimento Social",
                    descricao = "ONGs focadas em educação, capacitação profissional e desenvolvimento comunitário",
                    textoBotao = "Explorar ONGs  >",
                    onButtonClick = {}
                )
                Spacer(modifier = Modifier.height(20.dp))
                OngCard(
                    imagem = R.drawable.coracao,
                    imagemDescricao = "Imagem de um coração com fundo vermelho",
                    titulo = "Saúde & Direitos Humanos",
                    descricao = "Organizações que trabalham com saúde pública, direitos humanos e assistência social",
                    textoBotao = "Explorar ONGs  >",
                    onButtonClick = {}
                )
                Spacer(modifier = Modifier.height(25.dp))
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 1.dp,
                    color = Color.LightGray
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {}, modifier = Modifier
                    .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.azul_padrao),
                        contentColor = Color.White
                    )
                ) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Três pontos vertical")
                    Text(
                        text = "Dicas de Voluntariado",
                        fontSize = 14.sp,
                        fontFamily = roboto
                    )
                }
                Text(
                    text = "Aprenda sobre voluntariado responsável e ética",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = roboto
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "ONG Hub - Conectando você às causas que importam",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = Color.DarkGray,
                    fontFamily = roboto
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(colorResource(id = R.color.azul_padrao))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "3 Categorias",
                            fontSize = 12.sp,
                            color = Color.DarkGray,
                            fontFamily = roboto
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF0DE314))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "ONGs Verificadas",
                            fontSize = 12.sp,
                            color = Color.DarkGray,
                            fontFamily = roboto
                        )
                    }
                }
            }
        }
    }
}