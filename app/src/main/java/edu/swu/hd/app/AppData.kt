package edu.swu.hd.app

import androidx.lifecycle.ViewModel
import okhttp3.OkHttpClient

class AppData : ViewModel() {
    // 初始化
    var isInit = false

    // 数据库管理
    lateinit var dbHelper: DbHelper

    // 网络管理
    val netHelper = NetHelper()

    fun init(main: MainActivity) {
        isInit = true
        // 数据库管理
        dbHelper = DbHelper(main, "hd_app.db", 1)
    }
}