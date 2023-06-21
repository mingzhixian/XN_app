package edu.swu.xn.app

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import java.text.SimpleDateFormat

class ReportActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_report)
    appData.publicTools.setStatusAndNavBar(this)
    draw()
  }

  @SuppressLint("SetTextI18n", "SimpleDateFormat")
  private fun draw() {
    // 显示加载框
    val alert = appData.publicTools.showLoading("加载中", this, false, null)
    appData.netHelper.get(
      url = getString(R.string.admin_url)
          + "/api/service-order/order-info/getOrderByID?orderId="
          + intent.extras!!.getString("orderId")
    ) {
      alert.cancel()
      if (it == null) return@get
      val data = it.getJSONObject("data")
      findViewById<TextView>(R.id.report_patient_name).text =
        "姓名：" + data.getString("patientUserName")
      findViewById<TextView>(R.id.report_patient_sex).text =
        "性别：" + if (data.getString("patientSex").toInt() == 0) "男" else "女"
      findViewById<TextView>(R.id.report_patient_age).text = "年龄：" + data.getString("patientAge")
      findViewById<TextView>(R.id.report_patient_card_id).text =
        "身份证号：" + data.getString("patientCardId")
      findViewById<TextView>(R.id.report_patient_phone).text =
        "手机号：" + data.getString("patientPhonenumber")
      findViewById<TextView>(R.id.report_patient_medical_history).text =
        "过往病史：" + data.getString("medicalHistory")
      findViewById<TextView>(R.id.report_doctor_name).text =
        "医师：" + data.getString("doctorRealName")
      findViewById<TextView>(R.id.report_doctor_sex).text =
        "性别：" + if (data.getString("doctorSex").toInt() == 0) "男" else "女"
      findViewById<TextView>(R.id.report_doctor_dept).text = "科室：" + data.getString("deptName")
      findViewById<TextView>(R.id.report_doctor_title).text = "职称：" + data.getString("title")
      val mDateFormat = SimpleDateFormat("yyyy-MM-dd")
      findViewById<TextView>(R.id.report_order_date).text =
        "就诊时间：" + mDateFormat.format((data.getLong("date") * 1000))
      findViewById<TextView>(R.id.report_order_amount).text =
        "就诊费(元)：" + data.getString("amount")
      findViewById<TextView>(R.id.report_order_opinions).text =
        "诊断意见：" + data.getString("opinions")
    }
  }

  fun onBackClick(view: View) {
    finish()
  }

  fun onSearchClick(view: View) {
    startActivity(Intent(this, SearchActivity::class.java))
  }
}