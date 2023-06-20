package edu.swu.xn.app

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.swu.xn.app.helper.RecyclerViewAdapter
import java.text.SimpleDateFormat
import java.util.Date

class OrderActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    appData.publicTools.setStatusAndNavBar(this)
    setContentView(R.layout.activity_order)
    if (intent.extras!!.getString("for") != "fullOrderList")
      findViewById<TextView>(R.id.activity_title).text = "未缴费订单"
    drawUI()
  }

  lateinit var orderListViewAdapter: RecyclerViewAdapter

  // 获取列表
  @SuppressLint("SimpleDateFormat", "SetTextI18n", "NotifyDataSetChanged")
  private fun drawUI() {
    // 显示加载框
    val url = if (intent.extras!!.getString("for") == "fullOrderList")
      "${appData.main.getString(R.string.admin_url)}/api/service-order/order-info/getOrderListByUserId?userId=${appData.userId}"
    else "${appData.main.getString(R.string.admin_url)}/api/service-order/order-info/getOrderListByUserIdAndStatus?userId=${appData.userId}&status=0"
    val alert = appData.publicTools.showLoading("加载中", this, false, null)
    appData.netHelper.get(url) {
      alert.cancel()
      if (it==null)return@get
      val orderList = it.getJSONArray("data")
      val orderListView = findViewById<RecyclerView>(R.id.order_list)
      orderListView.layoutManager = LinearLayoutManager(this)
      orderListViewAdapter = RecyclerViewAdapter(R.layout.order_list_item,
        { orderList.length() }) { holder, position ->
        val orderItem = orderList.getJSONObject(position)
        val mDateFormat = SimpleDateFormat("MM-dd")
        holder.itemView.findViewById<TextView>(R.id.order_list_item_name).text =
          orderItem.getString("patientUserName")
        holder.itemView.findViewById<TextView>(R.id.order_list_item_date).text =
          mDateFormat.format(orderItem.getLong("date") * 1000)
        holder.itemView.findViewById<TextView>(R.id.order_list_item_doctor).text =
          "医生：${orderItem.getString("doctorRealName")}(${orderItem.getString("title")})"
        val status = orderItem.getInt("orderStatus")
        holder.itemView.findViewById<ImageView>(R.id.order_list_item_status).setImageResource(
          when (status) {
            0 -> {
              holder.itemView.findViewById<TextView>(R.id.order_list_item_status_2)
                .setTextColor(resources.getColor(R.color.buttonColor))
              R.drawable.order_need_payment
            }

            1 -> {
              if (Date().time > orderItem.getLong("date") * 1000) {
                holder.itemView.findViewById<TextView>(R.id.order_list_item_status_3)
                  .setTextColor(resources.getColor(R.color.buttonColor))
              } else {
                holder.itemView.findViewById<TextView>(R.id.order_list_item_status_4)
                  .setTextColor(resources.getColor(R.color.buttonColor))
                holder.itemView.findViewById<TextView>(R.id.order_list_item_cancel).visibility =
                  View.GONE
              }
              R.drawable.order_doing
            }

            2 -> {
              holder.itemView.findViewById<LinearLayout>(R.id.order_list_item_status_list).visibility =
                View.GONE
              R.drawable.order_closed
            }

            3 -> {
              holder.itemView.findViewById<LinearLayout>(R.id.order_list_item_status_list).visibility =
                View.GONE
              R.drawable.order_canceled
            }

            else -> {
              holder.itemView.findViewById<LinearLayout>(R.id.order_list_item_status_list).visibility =
                View.GONE
              R.drawable.order_closed
            }
          }
        )
        holder.itemView.findViewById<Button>(R.id.order_list_item_cancel).setOnClickListener {
          val alert2 = appData.publicTools.showLoading("加载中", this, false, null)
          appData.netHelper.get(
            "${appData.main.getString(R.string.admin_url)}/api/service-order/order-info/cancelOrderById?orderId=${
              orderItem.getString(
                "orderId"
              )
            }"
          ) {
            alert2.cancel()
            if (it==null)return@get
            orderItem.put("orderStatus", 3)
            orderListViewAdapter.notifyDataSetChanged()
          }
        }
        holder.itemView.findViewById<LinearLayout>(R.id.order_list_item_layout).setOnClickListener {
          val intent = Intent(this, OrderDetailActivity::class.java)
          intent.putExtra("orderId", orderItem.getString("orderId"))
          startActivity(intent)
        }
      }
      orderListView.adapter = orderListViewAdapter
    }
  }

  fun onBackClick(view: View) {
    finish()
  }

  fun onSearchClick(view: View) {
    startActivity(Intent(this, SearchActivity::class.java))
  }
}