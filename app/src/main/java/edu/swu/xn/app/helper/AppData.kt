package edu.swu.xn.app.helper

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.ViewModel
import edu.swu.xn.app.MainActivity
import edu.swu.xn.app.R
import edu.swu.xn.app.appData
import edu.swu.xn.app.entity.Subject
import kotlinx.coroutines.MainScope
import java.util.LinkedList

@SuppressLint("StaticFieldLeak")
class AppData : ViewModel() {
  // 初始化
  var isInit = false

  // Main
  lateinit var main: MainActivity

  // 当前账户ID
  var hashId = ""

  // 用户ID
  var userId = 0

  // 用户名
  var userName = ""

  // 总协程域
  val mainScope = MainScope()

  // 设置值
  lateinit var settings: SharedPreferences

  // 数据库管理
  lateinit var dbHelper: DbHelper

  // 网络管理
  val netHelper = NetHelper()

  // 公共数据库
  lateinit var publicTools: PublicTools

  // 通知管理
  lateinit var notificationHelper: NotificationHelper

  // 科目诊室列表
  lateinit var subjectList: LinkedList<Subject>

  // 状态栏高度
  var statusBarHeight = 0

  fun init(m: MainActivity) {
    isInit = true
    main = m
    // 设置存储
    settings = main.getSharedPreferences("setting", Context.MODE_PRIVATE)
    // 数据库管理
    dbHelper = DbHelper(main, "hd_app.db", 1)
    // 通知管理
    notificationHelper = NotificationHelper(main)
    // 公共数据库
    publicTools = PublicTools(main)
    // 状态栏高度
    statusBarHeight = getStatusBar()
  }

  @SuppressLint("InternalInsetResource", "DiscouragedApi")
  private fun getStatusBar(): Int {
    // 获取状态栏高度
    val resourceId = main.resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) {
      main.resources.getDimensionPixelSize(resourceId)
    } else 0
  }
}