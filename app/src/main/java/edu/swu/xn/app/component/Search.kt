package edu.swu.xn.app.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 *  搜索框组件
 *
 * @param text mutableState String对象,用于更新文本
 * @param onTextChanged 搜索栏文本变化回调
 * @param onSearchDone 输入键盘完成事件回调
 * @param modifier 修饰器
 * @param placeholderText 提示文本
 * @param iconSize 图标大小
 * @param iconColor 图标颜色
 * @param containerColor 容器颜色
 * @param placeholderColor 提示文本颜色
 * @param textColor 文本颜色
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    text: String,
    modifier: Modifier = Modifier,
    onTextChanged: (String) -> Unit = {},
    onSearchDone: () -> Unit = {},
    placeholderText: String = "",
    iconSize: Dp = 20.dp,
    iconColor: Color = Color.Gray,
    containerColor: Color = Color.White,
    placeholderColor: Color = Color.LightGray,
    textColor: Color = Color.Black
) {
    val focusManager = LocalFocusManager.current
    TextField(
        modifier = modifier,
        value = text,
        /* 文本变化回调 */
        onValueChange = onTextChanged,
        /* 提示文本 */
        placeholder = { Text(placeholderText) },
        /* 先导图标 */
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(iconSize)
            )
        },
        /* 单行限制 */
        singleLine = true,
        /* 配色方案 */
        colors = TextFieldDefaults.textFieldColors(
            containerColor = containerColor,
            placeholderColor = placeholderColor,
            textColor = textColor,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        /* 图形 */
        shape = MaterialTheme.shapes.large,
        /* 键盘操作变焦 */
        keyboardActions = KeyboardActions(
            /* 键盘输入完成回调 */
            onDone = {
                focusManager.clearFocus()
                onSearchDone()
            }
        )
    )
}

@Preview
@Composable
fun SearchPreview() {
    var text by rememberSaveable {
        mutableStateOf("")
    }
    Search(
        text = text,
        onTextChanged = {
            text = it
        },
        placeholderText = "搜索..."
    )
}
