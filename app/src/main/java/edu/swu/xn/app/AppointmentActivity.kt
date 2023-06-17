package edu.swu.xn.app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class AppointmentActivity : AppCompatActivity() {

  lateinit var bottomAdapter: RecyclerViewAdapter
  lateinit var doctors: JSONArray

  @SuppressLint("NotifyDataSetChanged", "SimpleDateFormat", "SetTextI18n")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_appointment)
    // 上部分
    val topRecyclerView = findViewById<RecyclerView>(R.id.appointment_top)
    val linearLayoutManager = LinearLayoutManager(this)
    linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
    topRecyclerView.layoutManager = linearLayoutManager
    val mDateFormat = SimpleDateFormat("MM-dd")
    val date = Date()
    topRecyclerView.adapter = RecyclerViewAdapter(R.layout.appointment_top_item,
      { 20 }) { holder, position ->
      val tmpDate = date.time.plus(86400000 * position)
      holder.itemView.findViewById<TextView>(R.id.appointment_top_item_text).text =
        mDateFormat.format(tmpDate)
      // 默认选中第一个
      val layout = holder.itemView.findViewById<LinearLayout>(R.id.appointment_top_item_layout)
      if (position == 0) {
        layout.backgroundTintList =
          ContextCompat.getColorStateList(this, R.color.md_theme_surface)
      }
      layout.setOnClickListener {
        // 更新ui
        Log.e("aaaaa",topRecyclerView.childCount.toString())
        for (i in 0 until topRecyclerView.childCount) {
          val childItem =
            topRecyclerView.getChildAt(i)
              .findViewById<LinearLayout>(R.id.appointment_top_item_layout)
          childItem.backgroundTintList =
            ContextCompat.getColorStateList(this, R.color.md_theme_surfaceVariant)
        }
        it.backgroundTintList = ContextCompat.getColorStateList(this, R.color.md_theme_surface)
        // 更新下边栏
        getDoctors(tmpDate) {
          synchronized(bottomAdapter) {
            bottomAdapter.notifyDataSetChanged()
          }
        }
      }
    }
    // 下部分
    getDoctors(date.time) {
      val appointmentBottom = findViewById<RecyclerView>(R.id.appointment_bottom)
      appointmentBottom.layoutManager = LinearLayoutManager(this)
      bottomAdapter =
        RecyclerViewAdapter(R.layout.appointment_bottom_item,
          { doctors.length() }) { holder, position ->
          val tmpDoctor = doctors.getJSONObject(position)
          // 头像
          holder.itemView.findViewById<TextView>(R.id.appointment_bottom_item_image).text =
            tmpDoctor.getString("realName")
          // 姓名
          holder.itemView.findViewById<TextView>(R.id.appointment_bottom_item_name).text =
            tmpDoctor.getString("realName")
          // 头衔
          holder.itemView.findViewById<TextView>(R.id.appointment_bottom_item_title).text =
            "--${tmpDoctor.getString("title")}"
          // 介绍
          holder.itemView.findViewById<TextView>(R.id.appointment_bottom_item_introduce).text =
            tmpDoctor.getString("introduce")
          // 余量
          holder.itemView.findViewById<TextView>(R.id.appointment_bottom_item_count).text =
            "余量：${tmpDoctor.getString("count")}"
          // 价格
          holder.itemView.findViewById<TextView>(R.id.appointment_bottom_item_amount).text =
            "价格：${tmpDoctor.getString("amount")}"
          // 点击跳转
          holder.itemView.findViewById<LinearLayout>(R.id.appointment_bottom_item_layout)
            .setOnClickListener {
              // todo 跳转医生界面
//            val intent = Intent(this, AppointmentActivity::class.java)
//            intent.putExtra("id", tmpDoctor.getInt("id"))
//            startActivity(intent)
            }
        }
      appointmentBottom.adapter = bottomAdapter
    }
  }

  // 更新
  @SuppressLint("NotifyDataSetChanged")
  private fun getDoctors(tmpDate: Long, handle: () -> Unit) {
    // 显示加载框
    appData.showLoading("加载中", this, false, null)
    appData.netHelper.get(
      "${appData.main.getString(R.string.admin_url)}/api/service-product/product/getWareByDeptForDays?deptName=${
        intent.getStringExtra(
          "deptName"
        )
      }&dateSecond=${tmpDate / 1000}"
    ) {
      appData.loadingDialog.cancel()
      if (it.getInt("code") != 200) {
        Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show()
        return@get
      }
      doctors = it.getJSONObject("data").getJSONArray("doctors")
      handle()
    }
  }
}