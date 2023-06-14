package edu.swu.xn.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import edu.swu.xn.app.ui.theme.XN_appTheme
import edu.swu.xn.app.helper.AppData

lateinit var appData: AppData

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appData = ViewModelProvider(this).get(AppData::class.java)
        // 第一次使用
        if (appData.settings.getBoolean("FirstUse", true)) firstUse()
        // 检测登录
    }

    // 第一次使用
    private fun firstUse() {
        // 通知注册
        appData.notificationHelper.registerChannel()
        // 权限申请
        appData.notificationHelper.requireNotificationPermission()
        // 保存
        appData.settings.edit().apply {
            putBoolean("FirstUse", false)
            apply()
        }
    }
}