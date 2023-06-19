package edu.swu.xn.app.helper

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
import android.widget.Button
import android.widget.TextView
import edu.swu.xn.app.MainActivity
import edu.swu.xn.app.R

class PublicTools(private val main: MainActivity) {

  // 设置状态栏导航栏颜色
  fun setStatusAndNavBar(context: Activity) {
    // 导航栏
//    context.window.navigationBarColor = context.resources.getColor(R.color.background)
//    context.window.navigationBarDividerColor = context.resources.getColor(R.color.onBackground)
    // 状态栏
    context.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    context.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    context.window.statusBarColor = context.resources.getColor(R.color.background)
    context.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
  }

  fun dp2px(dp: Float): Float = dp * main.resources.displayMetrics.density

  fun px2dp(px: Float): Int = (px / main.resources.displayMetrics.density + 0.5f).toInt()

  // 显示加载框
  fun showLoading(
    text: String,
    context: Context,
    isCanCancel: Boolean,
    cancelFun: (() -> Unit)?
  ): AlertDialog {
    // 加载框
    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
    builder.setCancelable(false)
    val loadingDialog = builder.create()
    loadingDialog.setCanceledOnTouchOutside(false)
    loadingDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    val loading = LayoutInflater.from(context).inflate(R.layout.loading, null, false)
    loadingDialog.setView(loading)
    loading.findViewById<TextView>(R.id.loading_text).text = text
    if (isCanCancel) {
      loading.findViewById<Button>(R.id.loading_cancel).visibility = View.VISIBLE
      loading.findViewById<Button>(R.id.loading_cancel)
        .setOnClickListener { cancelFun?.let { it1 -> it1() } }
    } else loading.findViewById<Button>(R.id.loading_cancel).visibility = View.GONE
    loadingDialog.show()
    return loadingDialog
  }
}