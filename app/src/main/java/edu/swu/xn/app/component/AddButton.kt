package edu.swu.xn.app.component


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 *  添加按钮
 *
 * @param modifier 修饰器
 * @param text 按钮文本
 * @param onClick 点击事件回调
 * @param textColor 文本颜色
 * @param iconColor 图标颜色
 * @param iconSize 图标大小
 */
@Composable
fun AddButton(
    modifier:Modifier = Modifier,
    text: String = "立即添加",
    onClick: () -> Unit = {},
    textColor: Color = Color(0xFF307572),
    iconColor: Color = Color(0xFF307572),
    iconSize: Dp = 18.dp
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = textColor,
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(
            horizontal = 8.dp
        ),
        border = BorderStroke(
            width = 1.dp,
            color = textColor
        )
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(iconSize)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
fun AddButtonPreview() {
    AddButton()
}
