package edu.swu.xn.app.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 *  加载进度环
 */
@Composable
fun LoadingProgress(
    modifier: Modifier = Modifier,
    color: Color = Color.Cyan,
    backgroundColor: Color = Color.Transparent
) {
    Column(
        modifier = modifier
          .fillMaxSize()
          .background(backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            color = color
        )
        Spacer(Modifier.size(10.dp))
        Text(
            text = "加载中...",
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun LoadingProgressPreview() {
    LoadingProgress()
}
