package edu.swu.hd.app.helper

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import edu.swu.hd.app.activity.MainActivity
import kotlinx.coroutines.MainScope

class AppData : ViewModel() {
    // 初始化
    var isInit = false

    // 总协程域
    val mainScope= MainScope()

    // 设置值
    lateinit var settings: SharedPreferences

    // 数据库管理
    lateinit var dbHelper: DbHelper

    // 网络管理
    val netHelper = NetHelper()

    // 通知管理
    lateinit var notificationHelper: NotificationHelper

    fun init(main: MainActivity) {
        isInit = true
        // 设置存储
        settings = main.getSharedPreferences("setting", Context.MODE_PRIVATE)
        // 数据库管理
        dbHelper = DbHelper(main, "hd_app.db", 1)
        //通知管理
        notificationHelper = NotificationHelper(main)
    }
}