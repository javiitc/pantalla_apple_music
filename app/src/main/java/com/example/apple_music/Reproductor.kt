package com.example.apple_music

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private val ColorPrincipal = Color.White
private val ColorSecundario = Color.White.copy(alpha = 0.6f)
private val ColorPistaInactiva = Color.White.copy(alpha = 0.3f)

@Composable
fun PantallaReproductor() {
    val portada = R.drawable.x100pre
    val (colorArriba, colorAbajo) = coloresDePortada(portada)

    val duracion = 138f
    var posicion by remember { mutableFloatStateOf(0f) }
    var reproduciendo by remember { mutableStateOf(false) }

    LaunchedEffect(reproduciendo) {
        while (reproduciendo) {
            delay(1000)
            if (posicion < duracion) {
                posicion += 1f
            } else {
                reproduciendo = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(colorArriba, colorAbajo)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Spacer(Modifier.height(16.dp))
            Image(
                painter = painterResource(id = portada),
                contentDescription = "Imagen de portada",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Si Estuviesemos Juntos",
                        color = ColorPrincipal,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Bad Bunny",
                        color = ColorSecundario,
                        modifier = Modifier.padding(top = 2.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                // Botón "..." dentro de un círculo translúcido
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.15f))
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_more_horiz_24),
                        contentDescription = "Más opciones",
                        tint = ColorPrincipal,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
            BarraDuracion(
                posicion = posicion,
                duracion = duracion,
                onPosicionCambia = { posicion = it }
            )

            Spacer(Modifier.weight(1f))
            ControlesReproduccion(
                reproduciendo = reproduciendo,
                onPlayPause = { reproduciendo = !reproduciendo },
                onAnterior = { posicion = 0f },
                onSiguiente = {
                    posicion = 0f
                    reproduciendo = false
                }
            )

            Spacer(Modifier.weight(1f))
            BarraVolumen()

            Spacer(Modifier.height(24.dp))
            BotonesInferiores()
        }
    }
}

@Composable
fun ControlesReproduccion(
    reproduciendo: Boolean,
    onPlayPause: () -> Unit,
    onAnterior: () -> Unit,
    onSiguiente: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BotonControl(R.drawable.baseline_skip_previous_24, "Anterior", 44.dp, onAnterior)
        BotonControl(
            icono = if (reproduciendo) R.drawable.baseline_pause_24 else R.drawable.baseline_play_arrow_24,
            descripcion = if (reproduciendo) "Pausa" else "Reproducir",
            tamano = 64.dp,
            onClick = onPlayPause
        )
        BotonControl(R.drawable.baseline_skip_next_24, "Siguiente", 44.dp, onSiguiente)
    }
}

@Composable
fun BotonControl(
    @DrawableRes icono: Int,
    descripcion: String,
    tamano: Dp,
    onClick: () -> Unit,
    color: Color = ColorPrincipal
) {
    IconButton(onClick = onClick, modifier = Modifier.size(tamano + 16.dp)) {
        Icon(
            painter = painterResource(icono),
            contentDescription = descripcion,
            tint = color,
            modifier = Modifier.size(tamano)
        )
    }
}

@Composable
fun BotonesInferiores() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BotonControl(R.drawable.baseline_format_quote_24, "Letra", 24.dp, {}, ColorSecundario)
        BotonControl(R.drawable.baseline_airplay_24, "AirPlay", 24.dp, {}, ColorSecundario)
        BotonControl(R.drawable.baseline_format_list_bulleted_24, "A continuación", 24.dp, {}, ColorSecundario)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraVolumen() {
    val coloresVolumen = SliderDefaults.colors(
        activeTrackColor = ColorPrincipal,
        inactiveTrackColor = ColorPistaInactiva
    )
    var volumen by remember { mutableFloatStateOf(50f) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.baseline_volume_down_24),
            contentDescription = "Bajar volumen",
            tint = ColorSecundario,
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(8.dp))
        Slider(
            value = volumen,
            onValueChange = { volumen = it },
            valueRange = 0f..100f,
            modifier = Modifier.weight(1f),
            thumb = {},
            track = { estado ->
                SliderDefaults.Track(
                    sliderState = estado,
                    colors = coloresVolumen,
                    modifier = Modifier.height(6.dp),
                    thumbTrackGapSize = 0.dp,
                    drawStopIndicator = null
                )
            }
        )
        Spacer(Modifier.width(8.dp))
        Icon(
            painter = painterResource(R.drawable.baseline_volume_up_24),
            contentDescription = "Subir volumen",
            tint = ColorSecundario,
            modifier = Modifier.size(20.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraDuracion(
    posicion: Float,
    duracion: Float,
    onPosicionCambia: (Float) -> Unit
) {
    val coloresDuracion = SliderDefaults.colors(
        activeTrackColor = ColorPrincipal,
        inactiveTrackColor = ColorPistaInactiva
    )

    val tiempoTranscurrido = tiempo(posicion.toInt())
    val tiempoRestante = "-${tiempo(duracion.toInt() - posicion.toInt())}"

    Column(modifier = Modifier.fillMaxWidth()) {
        Slider(
            value = posicion,
            onValueChange = onPosicionCambia,
            valueRange = 0f..duracion,
            thumb = {},
            track = { estado ->
                SliderDefaults.Track(
                    sliderState = estado,
                    colors = coloresDuracion,
                    modifier = Modifier.height(6.dp),
                    thumbTrackGapSize = 0.dp,
                    drawStopIndicator = null
                )
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = tiempoTranscurrido, fontSize = 12.sp, color = ColorSecundario)
            Text(text = tiempoRestante, fontSize = 12.sp, color = ColorSecundario)
        }
    }
}

@Composable
fun coloresDePortada(@DrawableRes id: Int): Pair<Color, Color> {
    val imagen: ImageBitmap = ImageBitmap.imageResource(id)
    return remember(imagen) {
        val bitmap = android.graphics.Bitmap.createScaledBitmap(imagen.asAndroidBitmap(), 20, 20, true)
        val arriba = colorMedio(bitmap, 0 until 10)
        val abajo = colorMedio(bitmap, 10 until 20)
        Pair(
            lerp(arriba, Color.Black, 0.25f),
            lerp(abajo, Color.Black, 0.55f)
        )
    }
}

private fun colorMedio(bitmap: android.graphics.Bitmap, filas: IntRange): Color {
    var rojo = 0L
    var verde = 0L
    var azul = 0L
    var total = 0
    for (y in filas) {
        for (x in 0 until bitmap.width) {
            val pixel = bitmap.getPixel(x, y)
            rojo += android.graphics.Color.red(pixel)
            verde += android.graphics.Color.green(pixel)
            azul += android.graphics.Color.blue(pixel)
            total++
        }
    }
    return Color(
        red = (rojo / total).toInt(),
        green = (verde / total).toInt(),
        blue = (azul / total).toInt()
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewReproductor() {
    PantallaReproductor()
}

fun tiempo(segundosTotales: Int): String {
    val min = segundosTotales / 60
    val segundos = segundosTotales % 60
    return "%d:%02d".format(min, segundos)
}
