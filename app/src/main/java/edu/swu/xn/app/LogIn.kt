package edu.swu.xn.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import edu.swu.xn.app.ui.theme.XN_appTheme

class LogIn : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XN_appTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    drawUI()
                }
            }
        }
    }

    // 绘制UI
    @Preview(showBackground = true)
    @Composable
    private fun drawUI() {
        // 绘制背景
        setBackground()
    }

    // 输入框
    @Composable
    private fun setBackground() {
        ConstraintLayout {
            val (icon, image, card) = createRefs()
            // 右上角图标
            Icon(
                painter = painterResource(R.drawable.app_icon),
                contentDescription = null,
                modifier = Modifier.constrainAs(icon){
                    top.linkTo(parent.top, margin = 16.dp)
                }
            )
        }
    }
}