package edu.swu.xn.app.component


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.swu.xn.app.R

/**
 * 操作卡片
 *
 * @param modifier 修饰器
 * @param text 卡片文本
 * @param textColor 文本颜色
 * @param onClick 点击事件
 * @param icon 卡片图标
 * @param iconSize 图标大小
 */
@Composable
fun OperateItem(
    modifier: Modifier = Modifier,
    text: String = "",
    textColor: Color = Color.Gray,
    onClick: () -> Unit = {},
    icon: Painter = painterResource(id = R.drawable.usericon),
    iconSize: Dp = 50.dp
) {
    OutlinedButton(
        onClick = onClick,
        border = null,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = icon,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(iconSize)
                    .background(Color.Transparent)
            )
            Spacer(
                modifier = Modifier.size(4.dp)
            )
            Text(
                text = text,
                color = textColor,
                fontSize = 10.sp
            )
        }
    }


}

@Preview
@Composable
fun OperateItemPreview() {
    OperateItem(
        text = "挂号"
    )
}
