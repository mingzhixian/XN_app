package edu.swu.hd.app.helper

import androidx.lifecycle.ViewModel
import edu.swu.hd.app.activity.MainActivity

class AppData : ViewModel() {
    // 初始化
    var isInit = false

    // 数据库管理
    lateinit var dbHelper: DbHelper

    // 网络管理
    val netHelper = NetHelper()

    // 通知管理
    lateinit var notificationHelper: NotificationHelper

    fun init(main: MainActivity) {
        isInit = true
        // 数据库管理
        dbHelper = DbHelper(main, "hd_app.db", 1)
        //通知管理
        notificationHelper = NotificationHelper(main)
    }
}