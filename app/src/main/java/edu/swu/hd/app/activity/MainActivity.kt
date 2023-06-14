package edu.swu.hd.app.activity

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import edu.swu.hd.app.R
import edu.swu.hd.app.helper.AppData

lateinit var appData: AppData

class MainActivity : Activity(), ViewModelStoreOwner {
    companion object {
        var ViewModelStore: ViewModelStore? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

    override fun getViewModelStore(): ViewModelStore {
        if (ViewModelStore == null) {
            ViewModelStore = ViewModelStore()
        }
        return ViewModelStore!!
    }
}