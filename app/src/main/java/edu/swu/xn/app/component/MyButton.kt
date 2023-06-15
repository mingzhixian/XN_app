package edu.swu.xn.app.component

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import edu.swu.xn.app.R

class MyButton(
    private val modifier: Modifier = Modifier,
    private val text: String? = null,
    private val icon: Painter? = null,
    private val onClick: () -> Unit,
) {
    @Composable
    fun show() {
        Button(
            modifier = modifier,
            onClick = onClick,
            contentPadding = ButtonDefaults.ButtonWithIconContentPadding
        ) {
            if (icon != null) {
                Icon(
                    icon,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
            }
            if (text != null) {
                if (icon != null) Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(text)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun preview() {
    Column {
        MyButton(text = "测试", icon = painterResource(R.drawable.app_icon)) {
            Log.e("aaaa", "点击")
        }.show()
        MyButton(text = null, icon = painterResource(R.drawable.app_icon)) {
            Log.e("aaaa", "点击")
        }.show()
        MyButton(text = "测试", icon = null) {
            Log.e("aaaa", "点击")
        }.show()
    }
}