package edu.swu.xn.app

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.swu.xn.app.entity.Subject
import edu.swu.xn.app.helper.SubjectAdapter
import okhttp3.internal.notify
import java.util.LinkedList

class SubjectActivity : AppCompatActivity() {
  @SuppressLint("NotifyDataSetChanged")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_subject)
    // 显示加载框
    appData.showLoading("加载中", this, false, null)
    // 获取信息
    appData.getSubjectList {
      // 取消加载框
      appData.loadingDialog.cancel()
      var subSubjectList = appData.subjectList[0].subList
      lateinit var subjectRightAdapter: SubjectAdapter
      // 左部分
      val subjectLeft = findViewById<RecyclerView>(R.id.subject_left)
      subjectLeft.layoutManager = LinearLayoutManager(this)
      subjectLeft.adapter = SubjectAdapter(R.layout.subject_left_item,
        { appData.subjectList.size }) { holder, position ->
        holder.itemView.findViewById<TextView>(R.id.subject_left_item_text).text =
          appData.subjectList[position].name
        val layout = holder.itemView.findViewById<LinearLayout>(R.id.subject_left_item_layout)
        if (position == 0) {
          layout.backgroundTintList =
            ContextCompat.getColorStateList(this, R.color.md_theme_surface)
          layout.findViewById<View>(R.id.subject_left_item_select).visibility = View.VISIBLE
        }
        layout.setOnClickListener {
          // 更新ui
          for (i in 0 until appData.subjectList.size) {
            val childItem =
              subjectLeft.getChildAt(i).findViewById<LinearLayout>(R.id.subject_left_item_layout)
            childItem.backgroundTintList =
              ContextCompat.getColorStateList(this, R.color.md_theme_surfaceVariant)
            childItem.findViewById<View>(R.id.subject_left_item_select).visibility = View.GONE
          }
          it.backgroundTintList = ContextCompat.getColorStateList(this, R.color.md_theme_surface)
          it.findViewById<View>(R.id.subject_left_item_select).visibility = View.VISIBLE
          // 更新右边栏
          subSubjectList = appData.subjectList[position].subList
          synchronized(subjectRightAdapter) {
            subjectRightAdapter.notifyDataSetChanged()
          }
        }
      }
      // 右部分
      val subjectRight = findViewById<RecyclerView>(R.id.subject_right)
      subjectRight.layoutManager = LinearLayoutManager(this)
      subjectRightAdapter =
        SubjectAdapter(R.layout.subject_right_item, { subSubjectList.size }) { holder, position ->
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
                // todo 跳转订号页面
                subList[i].name
              }
              rightList.addView(listItem)
            }
          }
        }
      subjectRight.adapter = subjectRightAdapter
    }
  }
}