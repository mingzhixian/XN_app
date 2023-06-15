package edu.swu.xn.app.component

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.swu.xn.app.R

class MyTextField(
  private val label: String? = null,
  private val text: String,
  private val icon: Painter? = null,
  private val handle: (response: String) -> Unit
) {
  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun show() {
    Column {
      TextField(
        value = text,
        onValueChange = handle,
        maxLines = 2,
        label = {
          if (label != null) {
            Text(
              label, modifier = Modifier.padding(15.dp, 0.dp, 0.dp, 0.dp)
            )
          }
        },
        leadingIcon = {
          if (icon != null) {
            Icon(
              icon,
              contentDescription = "Localized description",
              modifier = Modifier.size(ButtonDefaults.IconSize)
            )
          }
        },
        textStyle = TextStyle(fontWeight = FontWeight.Medium),
        modifier = Modifier.padding(10.dp, 4.dp, 10.dp, 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.textFieldColors(
          focusedIndicatorColor = Color.Transparent,
          unfocusedIndicatorColor = Color.Transparent
        )
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun preview() {
  var text by remember { mutableStateOf("") }
  Column {
    MyTextField("测试", text, painterResource(R.drawable.app_icon)) {
      text = it
    }.show()
    MyTextField(null, text, painterResource(R.drawable.app_icon)) {
      text = it
    }.show()
    MyTextField(null, text, null) {
      text = it
    }.show()
  }
}