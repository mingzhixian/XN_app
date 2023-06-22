package edu.swu.xn.app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.swu.xn.app.helper.RecyclerViewAdapter
import okhttp3.internal.notify
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class AppointmentActivity : AppCompatActivity() {

  lateinit var topAdapter: RecyclerViewAdapter
  lateinit var bottomAdapter: RecyclerViewAdapter
  lateinit var doctors: JSONArray

  @SuppressLint("NotifyDataSetChanged", "SimpleDateFormat", "SetTextI18n")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_appointment)
    appData.publicTools.setStatusAndNavBar(this)
    findViewById<TextView>(R.id.activity_title).text = intent.getStringExtra("deptName")
    // 上部分
    val topRecyclerView = findViewById<RecyclerView>(R.id.appointment_top)
    var selectItem = 0
    val linearLayoutManager = LinearLayoutManager(this)
    linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
    topRecyclerView.layoutManager = linearLayoutManager
    val mDateFormat = SimpleDateFormat("MM-dd")
    val date = Date()
    topAdapter = RecyclerViewAdapter(R.layout.appointment_top_item,
      { 20 }) { holder, position ->
      val tmpDate = date.time.plus(86400000 * position)
      holder.itemView.findViewById<TextView>(R.id.appointment_top_item_text).text =
        mDateFormat.format(tmpDate)
      // 默认选中第一个
      val layout = holder.itemView.findViewById<LinearLayout>(R.id.appointment_top_item_layout)
      if (position == selectItem) {
        layout.findViewById<TextView>(R.id.appointment_top_item_text)
          .setTextColor(this.resources.getColor(R.color.onCardBackground))
        layout.findViewById<View>(R.id.appointment_top_item_select).visibility = View.VISIBLE
      } else {
        layout.findViewById<TextView>(R.id.appointment_top_item_text)
          .setTextColor(this.resources.getColor(R.color.onCardBackgroundSecond))
        layout.findViewById<View>(R.id.appointment_top_item_select).visibility = View.GONE
      }
      layout.setOnClickListener {
        selectItem = position
        synchronized(topAdapter) {
          topAdapter.notifyDataSetChanged()
        }
        // 更新下边栏
        getDoctors(tmpDate) {
          synchronized(bottomAdapter) {
            bottomAdapter.notifyDataSetChanged()
          }
        }
      }
    }
    topRecyclerView.adapter = topAdapter
    // 下部分
    getDoctors(date.time) {
      val appointmentBottom = findViewById<RecyclerView>(R.id.appointment_bottom)
      appointmentBottom.layoutManager = LinearLayoutManager(this)
      bottomAdapter =
        RecyclerViewAdapter(R.layout.appointment_bottom_item,
          { doctors.length() }) { holder, position ->
          val tmpDoctor = doctors.getJSONObject(position)
          // 头像
          appData.netHelper.getImg(
            this,
            tmpDoctor.getString("avatar"),
            holder.itemView.findViewById(R.id.appointment_bottom_item_image)
          )
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
              val intent = Intent(this, DoctorActivity::class.java)
              intent.putExtra("productId", tmpDoctor.getString("productId"))
              intent.putExtra("realName", tmpDoctor.getString("realName"))
              intent.putExtra("avatar", tmpDoctor.getString("avatar"))
              intent.putExtra("amount", tmpDoctor.getString("amount"))
              intent.putExtra("title", tmpDoctor.getString("title"))
              intent.putExtra("sex", tmpDoctor.getString("sex"))
              intent.putExtra("count", tmpDoctor.getString("count"))
              intent.putExtra("amount", tmpDoctor.getString("amount"))
              val introduce = tmpDoctor.getString("introduce")
              intent.putExtra(
                "introduce",
                if (introduce.length > 100) introduce.substring(0, 100) else introduce
              )
              intent.putExtra("forWhat", "forCreatrOrder")
              startActivity(intent)
            }
        }
      appointmentBottom.adapter = bottomAdapter
    }
  }

  // 更新
  @SuppressLint("NotifyDataSetChanged")
  private fun getDoctors(tmpDate: Long, handle: () -> Unit) {
    // 显示加载框
    val alert = appData.publicTools.showLoading("加载中", this, false, null)
    appData.netHelper.get(
      "${appData.main.getString(R.string.admin_url)}/api/service-product/product/getWareByDeptForDays?deptName=${
        intent.getStringExtra(
          "deptName"
        )
      }&dateSecond=${tmpDate / 1000}"
    ) {
      alert.cancel()
      if (it == null) return@get
      doctors = it.getJSONArray("data")
      handle()
    }
  }

  fun onBackClick(view: View) {
    finish()
  }

  fun onSearchClick(view: View) {
    startActivity(Intent(this, SearchActivity::class.java))
  }
}