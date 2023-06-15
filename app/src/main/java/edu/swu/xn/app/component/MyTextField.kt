package edu.swu.xn.app.component

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.swu.xn.app.R
import edu.swu.xn.app.ui.theme.AppTheme

class MyTextField(
  private val label: String? = null,
  private val icon: Painter? = null,
  private val isHide: Boolean = false,
  private val handleOnChange: (response: String) -> Unit,
  private val handleOnDone: (response: String) -> Unit
) {
  private var text = ""

  @Composable
  fun show() {
    AppTheme {
      Row(
        Modifier
          .fillMaxWidth()
          .background(
            MaterialTheme.colorScheme.secondaryContainer,
            shape = RoundedCornerShape(12.dp)
          )
          .height(60.dp)
          .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        if (icon != null) {
          Icon(
            icon,
            contentDescription = "Localized description",
            modifier = Modifier.size(ButtonDefaults.IconSize)
          )
        }
        val focusManager = LocalFocusManager.current
        BasicTextField(
          value = text,
          onValueChange = {
            text = it
            handleOnChange(text)
          },
          visualTransformation = if (isHide) PasswordVisualTransformation() else VisualTransformation.None,
          singleLine = true,
          modifier = Modifier
            .weight(1f)
            .padding(start = 10.dp),
          textStyle = TextStyle(
            fontSize = 16.sp
          ),
          keyboardActions = KeyboardActions(
            onDone = {
              focusManager.clearFocus()
              handleOnDone(text)
            }
          )
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun preview() {
  Column {
    MyTextField(
      "测试",
      painterResource(R.drawable.app_icon),
      false,
      { Log.e("change", it) },
      { Log.e("done", it) }).show()
    MyTextField(
      null,
      painterResource(R.drawable.app_icon),
      false,
      { Log.e("change", it) },
      { Log.e("done", it) }).show()
    MyTextField(null, null, false,{ Log.e("change", it) }, { Log.e("done", it) }).show()
  }
}