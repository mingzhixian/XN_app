package edu.swu.xn.app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import edu.swu.xn.app.component.MyButton
import edu.swu.xn.app.component.MyTextField
import edu.swu.xn.app.ui.theme.AppTheme

class LogIn : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent { drawUI() }
  }

  // 绘制UI
  @Preview(showBackground = true)
  @Composable
  private fun drawUI() {
    AppTheme {
      Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
      ) {
        AndroidView(factory = { mContext ->
          LayoutInflater.from(mContext).inflate(R.layout.log_in, null, false)
        }) { rootView ->
          val composeViewLayout = rootView.findViewById<ComposeView>(R.id.log_in_view)
          composeViewLayout.setContent {
            var user = ""
            var password = ""
            // 输入框
            val userTextField =
              MyTextField(
                "手机号",
                painterResource(R.drawable.user),
                false,
                { },
                { user = it })
            val passwordTextField = MyTextField(
              "密码",
              painterResource(R.drawable.password),
              true,
              { },
              { password = it })
            val logInButton = MyButton("登录", null) {
              Log.e("logIn", "$user,$password")
            }
            // 按键

            // 显示
            userTextField.show()
            passwordTextField.show()
          }
        }
      }
    }
  }
}