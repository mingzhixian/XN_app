package edu.swu.xn.app

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.swu.xn.app.helper.RecyclerViewAdapter
import org.json.JSONObject

class DoctorActivity : AppCompatActivity() {

  private lateinit var clock: GridLayout
  private val subProductIdList = Array(9) { 0 }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    appData.publicTools.setStatusAndNavBar(this)
    setContentView(R.layout.activity_doctor)
    clock = findViewById(R.id.doctor_clock)
    // 设置填充值
    setValue()
  }

  // 设置填充值
  @SuppressLint("SetTextI18n")
  private fun setValue() {
    val extra = intent.extras!!
    val realName = extra.getString("realName")
    findViewById<TextView>(R.id.doctor_image).text = realName
    findViewById<TextView>(R.id.doctor_name).text = realName
    findViewById<TextView>(R.id.doctor_sex).text =
      "性别：${if (extra.getString("sex")!!.toInt() == 0) "男" else "女"}"
    findViewById<TextView>(R.id.doctor_title).text = extra.getString("title")
    findViewById<TextView>(R.id.doctor_introduce).text = extra.getString("introduce")
    if (extra.getString("forWhat") == "forDoctorDetail") {
      findViewById<LinearLayout>(R.id.doctor_lite_bar).visibility = View.GONE
      findViewById<GridLayout>(R.id.doctor_clock).visibility = View.GONE
      return
    }
    findViewById<TextView>(R.id.doctor_count).text = "余量：${extra.getString("count")}"
    findViewById<TextView>(R.id.doctor_amount).text = "价格：${extra.getString("amount")}"
    // 获取每个时间段是否空余
    // 显示加载框
    val alert = appData.publicTools.showLoading("加载中", this, false, null)
    appData.netHelper.get(
      "${appData.main.getString(R.string.admin_url)}/api/service-ware/ware/getDoctorProductByTime?productId=${
        extra.getString("productId")
      }"
    ) {
      alert.cancel()
      if (it == null) return@get
      val hours = it.getJSONArray("data")
      for (i in 0 until hours.length()) {
        subProductIdList[i] = hours.getJSONObject(i).getString("productId").toInt()
        if (hours.getJSONObject(i).getInt("count") == 0) {
          val child = clock.getChildAt(i)
          child.tag = -1
          (child as TextView).setTextColor(resources.getColor(R.color.onBackgroundSecond))
          child.backgroundTintList =
            ContextCompat.getColorStateList(this, R.color.cardContainerBackground)
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
    val alert = appData.publicTools.showLoading("加载中", this, false, null)
    val post1 = JSONObject()
    post1.put("userID", appData.userId)
    appData.netHelper.get(
      "${appData.main.getString(R.string.admin_url)}/api/service-user/patient/getPatientInfo",
      post1
    ) {
      alert.cancel()
      if (it == null) return@get
      val patientListData = it.getJSONArray("data")
      // 显示就诊人列表
      val builder: AlertDialog.Builder = AlertDialog.Builder(this)
      builder.setCancelable(false)
      val patientDialog = builder.create()
      patientDialog.setCanceledOnTouchOutside(true)
      patientDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
      val patientListView = LayoutInflater.from(this).inflate(R.layout.patient_list, null, false)
      patientDialog.setView(patientListView)
      // 设置就诊人列表
      val patientList = patientListView.findViewById<RecyclerView>(R.id.patient_list)
      patientList.layoutManager = LinearLayoutManager(this)
      val patientListAdapter = RecyclerViewAdapter(R.layout.patient_list_item,
        { patientListData.length() }) { holder, position ->
        holder.itemView.findViewById<TextView>(R.id.patient_list_item_text).text =
          patientListData.getJSONObject(position).getString("userName")
        // 选定就诊人下订单
        holder.itemView.findViewById<LinearLayout>(R.id.patient_list_item_layout)
          .setOnClickListener {
            patientDialog.cancel()
            // 显示加载框
            val alert2 = appData.publicTools.showLoading("加载中", this, false, null)
            val post2 = JSONObject()
            post2.put("userId", appData.userId)
            post2.put("productId", subProductIdList[view.tag.toString().toInt() - 1])
            post2.put("patientID", patientListData.getJSONObject(position).getString("id").toInt())
            post2.put("type", 0)
            appData.netHelper.get(
              "${appData.main.getString(R.string.admin_url)}/api/service-order/order-info/createOrder",
              post2
            ) {
              alert2.cancel()
              if (it == null) return@get
              val intent = Intent(this, PayActivity::class.java)
              intent.putExtra("orderId", it.getJSONObject("data").getString("orderId"))
              val amountText = findViewById<TextView>(R.id.doctor_amount).text
              intent.putExtra("amount", amountText.substring(3, amountText.length))
              startActivity(intent)
            }
          }
      }
      patientList.adapter = patientListAdapter
      patientDialog.show()
    }
  }

  fun onBackClick(view: View) {
    finish()
  }

  fun onSearchClick(view: View) {
    startActivity(Intent(this, SearchActivity::class.java))
  }
}