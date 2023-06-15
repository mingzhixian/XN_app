package edu.swu.xn.app.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview

class MyButton(
    private val modifier: Modifier = Modifier,
    private val text: String? = null,
    private val icon: Painter? = null,
    private val onClick: () -> Unit,
    private val containerColor: Color = Color.Blue,
    private val contentColor: Color = Color.White
) {
    @Composable
    fun show() {
        Button(
            modifier = modifier,
            onClick = onClick,
            contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor
            )
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
    MyButton(onClick = {}).show()
}