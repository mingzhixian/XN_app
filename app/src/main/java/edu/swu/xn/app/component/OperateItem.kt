package edu.swu.xn.app.component


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun OperateItem(
    modifier: Modifier = Modifier,
    text: String = "",
    textColor: Color = Color.LightGray,
    onClick: () -> Unit = {},
    icon: ImageVector = Icons.Filled.Done,
    iconSize: Dp = 80.dp
) {
    Column(
        modifier = modifier.border(width = 0.2f.dp, color = Color.DarkGray),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedButton(
            onClick = onClick,
            border = null,
        ) {
            Image(
                imageVector = icon,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(iconSize)
                    .background(Color.Transparent)
            )
        }
        Spacer(
            modifier = Modifier.size(4.dp)
        )
        Text(
            text = text,
            color = textColor
        )
    }


}

@Preview
@Composable
fun OperateItemPreview() {
    OperateItem(
        text = "挂号"
    )
}
