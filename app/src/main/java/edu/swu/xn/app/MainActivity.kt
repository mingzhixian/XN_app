package edu.swu.xn.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import edu.swu.xn.app.helper.AppData

lateinit var appData: AppData

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appData = ViewModelProvider(this).get(AppData::class.java)
        if (!appData.isInit) appData.init(this)
        // 第一次使用
        if (appData.settings.getBoolean("FirstUse", true)) firstUse()
        // 检测登录

        startActivity(Intent(
            this,
            LogIn::class.java
        ))
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