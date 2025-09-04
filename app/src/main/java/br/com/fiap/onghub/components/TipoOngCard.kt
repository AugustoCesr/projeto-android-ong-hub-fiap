package br.com.fiap.onghub.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.onghub.ui.theme.roboto

@Composable
fun TipoOngCard(
    imagem: Int,
    descricaoImagem: String,
    tituloTipoOng: String,
    descricao: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = imagem),
                    contentDescription = descricaoImagem,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(shape = CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column() {
                    Text(
                        text = tituloTipoOng,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = roboto
                    )
                    Text(
                        text = "3 organizações",
                        fontSize = 12.sp,
                        fontFamily = roboto
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = descricao,
                fontSize = 14.sp,
                fontFamily = roboto
            )
        }
    }
}