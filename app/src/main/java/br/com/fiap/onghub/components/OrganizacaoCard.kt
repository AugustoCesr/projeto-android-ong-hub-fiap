package br.com.fiap.onghub.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.onghub.R
import br.com.fiap.onghub.ui.theme.roboto

@Composable
fun OrganizacaoCard(
    imagem: Int,
    descricaoImagem: String,
    nomeOng: String,
    descricaoOng: String,
    localidade: String,
    textoBotao: String,
    onButtonClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 32.dp)
        ) {
            Image(
                painter = painterResource(id = imagem),
                contentDescription = descricaoImagem,
                modifier = Modifier
                    .size(80.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column() {
                Text(
                    text = nomeOng,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = roboto
                )
                Text(
                    text = descricaoOng,
                    fontSize = 14.sp,
                    fontFamily = roboto
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = localidade,
                        fontSize = 12.sp,
                        fontFamily = roboto
                    )
                    OutlinedButton(
                        onClick = onButtonClick,
                        border = BorderStroke(1.dp, Color.White),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = colorResource(id = R.color.azul_padrao)
                        )
                    ) {
                        Text(
                            text = textoBotao,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = roboto
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Ícone de seta"
                        )
                    }
                }
            }
        }
    }
}