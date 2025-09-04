package br.com.fiap.onghub.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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

@Composable
fun Header(
    titulo: String,
    onButtonClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(colorResource(id = R.color.azul_padrao))
    ) {
        OutlinedButton(
            onClick = onButtonClick,
            modifier = Modifier.offset(x = (-10.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.azul_padrao),
                contentColor = Color.White
            ),
            border = BorderStroke(width = 1.dp, color = colorResource(id = R.color.azul_padrao))
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
        }
        Text(
            text = titulo,
            modifier = Modifier.offset((-10.dp)),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = nunito,
            color = Color.White
        )
    }
}