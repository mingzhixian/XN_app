package edu.swu.xn.app.component


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 *  主页普通卡片
 *
 *  @param modifier 修饰器
 *  @param titleText 标题文本
 *  @param contentText 内容文本
 *  @param onClick 点击时间回调
 *  @param titleTextSize 标题文本大小
 *  @param contentTextSize 内容文本大小
 *  @param textColor 文本颜色
 *  @param backgroundColor 背景颜色
 */
@Composable
fun SimpleCard(
    modifier: Modifier = Modifier,
    titleText: String = "",
    contentText: String = "",
    onClick: () -> Unit = {},
    titleTextSize: Int = 18,
    contentTextSize: Int = 10,
    textColor: Color = Color(0xFF517476),
    backgroundColor: Color = Color(0xFFA4CDD3)
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            contentColor = textColor,
            containerColor = backgroundColor,
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = titleText,
                fontSize = titleTextSize.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = contentText,
                fontSize = contentTextSize.sp
            )
        }
    }
}

@Preview
@Composable
fun SimpleCardPreview() {
    SimpleCard(
        titleText = "预约挂号",
        contentText = "精准预约，疾病百科"
    )
}
