package edu.swu.xn.app.helper

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import edu.swu.xn.app.MainActivity
import kotlinx.coroutines.MainScope

@SuppressLint("StaticFieldLeak")
class AppData(val main: MainActivity) : ViewModel() {
  // 当前账户ID
  var hashID = ""

  // 总协程域
  val mainScope = MainScope()

  // 设置值
  var settings: SharedPreferences = main.getSharedPreferences("setting", Context.MODE_PRIVATE)

  // 数据库管理
  var dbHelper: DbHelper = DbHelper(main, "hd_app.db", 1)

  // 网络管理
  val netHelper = NetHelper()

  // 通知管理
  var notificationHelper: NotificationHelper = NotificationHelper(main)

  init {
  }

  fun dp2px(dp: Float): Float = dp * main.resources.displayMetrics.density
}