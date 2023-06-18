package edu.swu.xn.app.component


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun TopRoundBackground(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    containerColor: Color = Color.Green,
    offset: Float = -160f,
) {
    /* 顶部背景椭圆 */
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        scale(scaleX = 15f, scaleY = 10f) {
            drawCircle(
                color = containerColor,
                radius = 40.dp.toPx(),
                center = this.center + Offset(0f, offset)
            )
        }
    }
}

@Preview
@Composable
fun TopRoundBackgroundPreview() {
    TopRoundBackground()
}
