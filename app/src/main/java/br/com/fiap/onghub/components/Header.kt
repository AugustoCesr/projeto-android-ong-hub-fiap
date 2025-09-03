package br.com.fiap.onghub.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.onghub.R
import br.com.fiap.onghub.ui.theme.nunito
import br.com.fiap.onghub.ui.theme.roboto

@Composable
fun Header() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(colorResource(id = R.color.azul_padrao))
    ) {
        Text(
            text = "ONG Hub",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = nunito,
            color = Color.White
        )
        Text(
            text = "Conectando você às causas que importam",
            fontSize = 12.sp,
            fontFamily = roboto,
            color = Color.White
        )
    }
}