package edu.swu.xn.app.helper

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import edu.swu.xn.app.MainActivity
import edu.swu.xn.app.R
import edu.swu.xn.app.appData
import edu.swu.xn.app.entity.Subject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.withContext
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
  var statusBar = 0

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
    statusBar = getStatusBar()
  }

  // 获取科目诊室列表
  fun getSubjectList(handle: (() -> Unit)) {
    netHelper.get("${main.getString(R.string.admin_url)}/api/service-user/dept-category/getCategory") {
      subjectList = LinkedList<Subject>()
      // 请求错误
      if (it.getInt("code") != 200) {
        Toast.makeText(main, "未知错误", Toast.LENGTH_SHORT).show()
      } else {
        // 解析请求
        val data = it.getJSONArray("data")
        for (i in 0 until data.length()) {
          // 第一大类
          val sub1Json = data.getJSONObject(i)
          val sub1Obj =
            Subject(sub1Json.getString("deptName"), sub1Json.getInt("id"), LinkedList<Subject>())
          // 第二大类
          val sub2Json = sub1Json.getJSONArray("childCategory")
          for (i2 in 0 until sub2Json.length()) {
            val sub3Json = sub2Json.getJSONObject(i2)
            val sub3Obj =
              Subject(sub3Json.getString("deptName"), sub3Json.getInt("id"), LinkedList<Subject>())
            // 第三大类
            val sub4Json = sub3Json.getJSONArray("childCategory")
            for (i3 in 0 until sub4Json.length()) {
              val sub5Json = sub4Json.getJSONObject(i3)
              val sub5Obj =
                Subject(
                  sub5Json.getString("deptName"),
                  sub5Json.getInt("id"),
                  LinkedList<Subject>()
                )
              sub3Obj.subList.add(sub5Obj)
            }
            sub1Obj.subList.add(sub3Obj)
          }
          subjectList.add(sub1Obj)
        }
        appData.main.runOnUiThread { handle() }
      }
    }
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