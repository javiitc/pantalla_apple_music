package com.example.apple_music

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaReproductor() {
    Column (modifier = Modifier.fillMaxSize().padding(24.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.x100pre),
            contentDescription = "Imagen de portada",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer (modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Column (modifier = Modifier.weight(1f)) {
                Text (
                    text = "Si Estuviesemos Juntos",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text (
                    text = "Bad Bunny",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Spacer (Modifier.weight(1f))
        BarraVolumen()
        Spacer (Modifier.height(32.dp))
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraVolumen () {

    val coloresVolumen = SliderDefaults.colors(
        activeTrackColor = Color(0xFFC8102E),
        inactiveTrackColor = Color(0xFFE0E0E0)
    )
    var volumen by remember { mutableFloatStateOf(50f) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Slider (
            value = volumen,
            onValueChange = { volumen = it },
            valueRange = 0f..100f,
            thumb = {},
            track = {estado ->
                SliderDefaults.Track (
                    sliderState = estado,
                    colors = coloresVolumen,
                    modifier = Modifier.height(6.dp),
                    thumbTrackGapSize = 0.dp,
                    drawStopIndicator = null
                )

            }
        )

        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text (text = "-")
            Text (text = "+")
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewReproductor () {
    PantallaReproductor()
}
