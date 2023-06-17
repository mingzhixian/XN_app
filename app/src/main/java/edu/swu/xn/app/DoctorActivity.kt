package edu.swu.xn.app

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.swu.xn.app.helper.RecyclerViewAdapter
import org.json.JSONObject

class DoctorActivity : AppCompatActivity() {

  lateinit var clock: GridLayout
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_doctor)
    clock = findViewById<GridLayout>(R.id.doctor_clock)
    // 设置填充值
    setValue()
  }

  // 设置填充值
  @SuppressLint("SetTextI18n")
  private fun setValue() {
    findViewById<TextView>(R.id.doctor_image).text = intent.getStringExtra("realName")
    findViewById<TextView>(R.id.doctor_name).text = intent.getStringExtra("realName")
    findViewById<TextView>(R.id.doctor_sex).text = "性别：${intent.getStringExtra("sex")}"
    findViewById<TextView>(R.id.doctor_title).text = intent.getStringExtra("title")
    findViewById<TextView>(R.id.doctor_introduce).text = intent.getStringExtra("introduce")
    findViewById<TextView>(R.id.doctor_count).text = "余量：${intent.getStringExtra("count")}"
    findViewById<TextView>(R.id.doctor_amount).text = "价格：${intent.getStringExtra("amount")}"
    // 获取每个时间段是否空余
    // 显示加载框
    appData.showLoading("加载中", this, false, null)
    appData.netHelper.get(
      "${appData.main.getString(R.string.admin_url)}/api/service-product/product/getDoctorProductByTime?productId=${
        intent.getStringExtra(
          "productId"
        )
      }"
    ) {
      appData.loadingDialog.cancel()
      if (it.getInt("code") != 200) {
        Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show()
        return@get
      }
      val hours = it.getJSONObject("data").getJSONArray("hours")
      for (i in 0 until hours.length()) {
        if (hours.getJSONObject(i).getInt("count") == 0) {
          val child = clock.getChildAt(i)
          child.tag = -1
          (child as TextView).setTextColor(resources.getColor(R.color.md_theme_onSurfaceVariant))
          child.backgroundTintList =
            ContextCompat.getColorStateList(this, R.color.md_theme_onSecondary)
        }
      }
    }
  }

  // 点击
  fun onClockItemClick(view: View) {
    if (view.tag == 0) {
      Toast.makeText(this, "已订满", Toast.LENGTH_SHORT).show()
      return
    }
    // 获取就诊人列表
    // 显示加载框
    appData.showLoading("加载中", this, false, null)
    val post1 = JSONObject()
    post1.put("userID", appData.userID)
    appData.netHelper.get(
      "${appData.main.getString(R.string.admin_url)}/api/service-user/patient/getPatientInfo",
      post1
    ) {
      appData.loadingDialog.cancel()
      if (it.getInt("code") != 200) {
        Toast.makeText(this, "获取就诊人失败", Toast.LENGTH_SHORT).show()
        return@get
      }
      val patientListData = it.getJSONArray("data")
      // 显示就诊人列表
      val builder: AlertDialog.Builder = AlertDialog.Builder(this)
      builder.setCancelable(false)
      val patientDialog = builder.create()
      patientDialog.setCanceledOnTouchOutside(true)
      patientDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
      val patientListView = LayoutInflater.from(this).inflate(R.layout.patient_list, null, false)
      patientDialog.setView(patientListView)
      val patientList = patientListView.findViewById<RecyclerView>(R.id.patient_list)
      patientList.layoutManager = LinearLayoutManager(this)
      val patientListAdapter = RecyclerViewAdapter(R.layout.subject_left_item,
        { patientListData.length() }) { holder, position ->
        holder.itemView.findViewById<TextView>(R.id.patient_list_item_text).text =
          patientListData.getJSONObject(position).getString("userName")
        // 选定就诊人下订单
        holder.itemView.findViewById<TextView>(R.id.patient_list_item_layout).setOnClickListener {
          // 显示加载框
          appData.showLoading("加载中", this, false, null)
          val post = JSONObject()
          post.put("userID", appData.userID)
          post.put("productId", intent.getStringExtra("productId"))
          post.put("offset", view.tag)
          post.put("patientID", patientListData.getJSONObject(position).getString("userId"))
          appData.netHelper.get(
            // todo 下订单url
            "${appData.main.getString(R.string.admin_url)}/api/service-product/product/getWareByDeptForDays",
            post
          ) {
            appData.loadingDialog.cancel()
            if (it.getInt("code") != 200) {
              Toast.makeText(this, "下单失败", Toast.LENGTH_SHORT).show()
              return@get
            }
            val intent = Intent(this, PayActivity::class.java)
            intent.putExtra("orderId", it.getJSONObject("data").getString("orderId"))
            startActivity(intent)
          }
        }
      }
      patientList.adapter = patientListAdapter
    }
  }
}