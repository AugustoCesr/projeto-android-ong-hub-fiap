package br.com.fiap.onghub.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.onghub.R
import br.com.fiap.onghub.components.Header
import br.com.fiap.onghub.components.OrganizacaoCard
import br.com.fiap.onghub.components.TipoOngCard
import br.com.fiap.onghub.ui.theme.roboto

@Composable
fun OrganizacoesScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Column() {
            Header(
                titulo = "Meio Ambiente & Bem-Estar Animal",
                onButtonClick = {}
            )
            Column(modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
            ) {
                TipoOngCard(
                    imagem = R.drawable.plantinha,
                    descricaoImagem = "Imagem de uma plantinha com fundo verde",
                    tituloTipoOng = "Meio Ambiente & Bem-Estar Animal",
                    descricao = "Organizações dedicadas à preservação ambiental, proteção animal e sustentabilidade"
                )
                Spacer(modifier = Modifier.height(30.dp))
                Row() {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_check_box_azul),
                        contentDescription = "Ícone de uma checkbox",
                        tint = colorResource(id = R.color.azul_padrao)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Organizações Disponíveis",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = roboto
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                OrganizacaoCard(
                    imagem = R.drawable.projeto_tamar,
                    descricaoImagem = "Logo do Projeto Tamar",
                    nomeOng = "Projeto Tamar",
                    descricaoOng = "Conservação de tartarugas marinhas e proteção dos oceanos brasileiros",
                    localidade = "Praia do Forte, BA",
                    textoBotao = "Ver detalhes",
                    onButtonClick = {}
                )
                Spacer(modifier = Modifier.height(16.dp))
                OrganizacaoCard(
                    imagem = R.drawable.sos_mata_atlantica,
                    descricaoImagem = "Logo do SOS Mata Atlântica",
                    nomeOng = "SOS Mata Atlântica",
                    descricaoOng = "Defesa e preservação da mata atlântica brasileira",
                    localidade = "São Paulo, SP",
                    textoBotao = "Ver detalhes",
                    onButtonClick = {}
                )
                Spacer(modifier = Modifier.height(16.dp))
                OrganizacaoCard(
                    imagem = R.drawable.uipa,
                    descricaoImagem = "Logo do UIPA - União Internacional Protetora dos Animais",
                    nomeOng = "UIPA - União Internacional Protetora dos Animais",
                    descricaoOng = "Proteção e defesa dos direitos dos animais",
                    localidade = "São Paulo, SP",
                    textoBotao = "Ver detalhes",
                    onButtonClick = {}
                )
            }
        }
    }
}