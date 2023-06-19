package edu.swu.xn.app

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.swu.xn.app.helper.RecyclerViewAdapter

class SubjectActivity : AppCompatActivity() {
  @SuppressLint("NotifyDataSetChanged")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    appData.publicTools.setFullScreen(this)
    setContentView(R.layout.activity_subject)
    try {
      appData.subjectList.size
      draw()
    } catch (_: Exception) {
      // 显示加载框
      val alert = appData.publicTools.showLoading("加载中", this, false, null)
      // 获取信息
      appData.getSubjectList {
        // 取消加载框
        alert.cancel()
        draw()
      }
    }
  }

  // 绘制ui
  @SuppressLint("NotifyDataSetChanged")
  private fun draw() {
    var subSubjectList = appData.subjectList[0].subList
    lateinit var subjectLeftAdapter: RecyclerViewAdapter
    lateinit var subjectRightAdapter: RecyclerViewAdapter
    var selectItem = 0
    // 左部分
    val subjectLeft = findViewById<RecyclerView>(R.id.subject_left)
    subjectLeft.layoutManager = LinearLayoutManager(this)
    subjectLeftAdapter = RecyclerViewAdapter(
      R.layout.subject_left_item,
      { appData.subjectList.size }) { holder, position ->
      holder.itemView.findViewById<TextView>(R.id.subject_left_item_text).text =
        appData.subjectList[position].name
      val layout = holder.itemView.findViewById<LinearLayout>(R.id.subject_left_item_layout)
      if (position == selectItem) {
        layout.findViewById<TextView>(R.id.subject_left_item_text)
          .setTextColor(this.resources.getColor(R.color.onCardBackground))
        layout.findViewById<View>(R.id.subject_left_item_select).visibility = View.VISIBLE
      } else {
        layout.findViewById<TextView>(R.id.subject_left_item_text)
          .setTextColor(this.resources.getColor(R.color.onCardBackgroundSecond))
        layout.findViewById<View>(R.id.subject_left_item_select).visibility = View.GONE
      }
      layout.setOnClickListener {
        selectItem = position
        synchronized(subjectLeftAdapter) {
          subjectLeftAdapter.notifyDataSetChanged()
        }
        // 更新右边栏
        subSubjectList = appData.subjectList[position].subList
        synchronized(subjectRightAdapter) {
          subjectRightAdapter.notifyDataSetChanged()
        }
      }
    }
    subjectLeft.adapter = subjectLeftAdapter
    // 右部分
    val subjectRight = findViewById<RecyclerView>(R.id.subject_right)
    subjectRight.layoutManager = LinearLayoutManager(this)
    subjectRightAdapter = RecyclerViewAdapter(R.layout.subject_right_item,
      { subSubjectList.size }) { holder, position ->
      val rightItem = holder.itemView.findViewById<LinearLayout>(R.id.subject_right_item_layout)
      val rightList = holder.itemView.findViewById<LinearLayout>(R.id.subject_right_item_list)
      holder.itemView.findViewById<TextView>(R.id.subject_right_item_text).text =
        subSubjectList[position].name
      val subList = subSubjectList[position].subList
      if (subList.size == 0) {
        rightList.visibility = View.GONE
      } else {
        rightList.removeAllViews()
        rightList.visibility = View.VISIBLE
        for (i in 0 until subList.size) {
          val listItem = LayoutInflater.from(appData.main)
            .inflate(R.layout.subject_right_item_list_item, rightItem, false)
          listItem.findViewById<TextView>(R.id.subject_right_item_list_item_text).text =
            subList[i].name
          listItem.setOnClickListener {
            val intent = Intent(this, AppointmentActivity::class.java)
            intent.putExtra("deptName", subList[i].name)
            startActivity(intent)
          }
          rightList.addView(listItem)
        }
      }
    }
    subjectRight.adapter = subjectRightAdapter
  }
}