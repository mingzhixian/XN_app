package edu.swu.xn.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.swu.xn.app.helper.RecyclerViewAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class AppointmentActivity : AppCompatActivity() {
  @SuppressLint("NotifyDataSetChanged", "SimpleDateFormat")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_appointment)
    lateinit var bottomAdapter: RecyclerViewAdapter
    // 上部分
    val topRecyclerView = findViewById<RecyclerView>(R.id.appointment_top)
    topRecyclerView.layoutManager = LinearLayoutManager(this)
    val sDateFormat = SimpleDateFormat("yyyy-MM-dd")
    val date = Date()
    topRecyclerView.adapter = RecyclerViewAdapter(R.layout.appointment_top_item,
      { 20 }) { holder, position ->
      holder.itemView.findViewById<TextView>(R.id.appointment_top_item_text).text = ""
//        "${sDateFormat.format(date + 86400 * position)}"
      val layout = holder.itemView.findViewById<LinearLayout>(R.id.appointment_top_item_layout)
      if (position == 0) {
        layout.backgroundTintList =
          ContextCompat.getColorStateList(this, R.color.md_theme_surface)
      }
      layout.setOnClickListener {
//        // 更新ui
//        for (i in 0 until data.length()) {
//          val childItem =
//            topRecyclerView.getChildAt(i)
//              .findViewById<LinearLayout>(R.id.appointment_top_item_layout)
//          childItem.backgroundTintList =
//            ContextCompat.getColorStateList(this, R.color.md_theme_surfaceVariant)
//        }
//        it.backgroundTintList = ContextCompat.getColorStateList(this, R.color.md_theme_surface)
//        // 更新下边栏
//        doctors = data.getJSONObject(position).getJSONArray("doctors")
//        synchronized(bottomAdapter) {
//          bottomAdapter.notifyDataSetChanged()
//        }
      }
    }
//    // 下部分
//    val appointmentBottom = findViewById<RecyclerView>(R.id.appointment_bottom)
//    appointmentBottom.layoutManager = LinearLayoutManager(this)
//    bottomAdapter =
//      RecyclerViewAdapter(
//        R.layout.subject_right_item,
//        { doctors.length() }) { holder, position ->
//        val rightItem = holder.itemView.findViewById<LinearLayout>(R.id.subject_right_item_layout)
//        val rightList = holder.itemView.findViewById<LinearLayout>(R.id.subject_right_item_list)
//        holder.itemView.findViewById<TextView>(R.id.subject_right_item_text).text =
//          subSubjectList[position].name
//        val subList = subSubjectList[position].subList
//        if (subList.size == 0) {
//          rightList.visibility = View.GONE
//        } else {
//          rightList.removeAllViews()
//          rightList.visibility = View.VISIBLE
//          for (i in 0 until subList.size) {
//            val listItem = LayoutInflater.from(appData.main)
//              .inflate(R.layout.subject_right_item_list_item, rightItem, false)
//            listItem.findViewById<TextView>(R.id.subject_right_item_list_item_text).text =
//              subList[i].name
//            listItem.setOnClickListener {
//              // todo 跳转订号页面
//              subList[i].name
//            }
//            rightList.addView(listItem)
//          }
//        }
//      }
//    appointmentBottom.adapter = bottomAdapter
//     显示加载框
    appData.showLoading("加载中", this, false, null)
    // 获取信息
    appData.netHelper.get(
      "${appData.main.getString(R.string.admin_url)}/api/service-user/dept-category/getCategory?deptName=${
        intent.getStringExtra(
          "deptName"
        )
      }"
    ) {
      // 取消加载框
      appData.loadingDialog.cancel()
      // 请求错误
      if (it.getInt("code") != 200) {
        Toast.makeText(appData.main, "未知错误", Toast.LENGTH_SHORT).show()
      } else {
        val data = it.getJSONArray("data")
        var doctors = data.getJSONObject(0).getJSONArray("doctors")

      }
    }
  }
}