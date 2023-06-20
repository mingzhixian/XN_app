package edu.swu.xn.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.swu.xn.app.component.LoadingProgress
import edu.swu.xn.app.component.TopBar
import edu.swu.xn.app.ui.theme.AppTheme
import java.text.DecimalFormat

class OrderDetailActivity : AppCompatActivity() {

  lateinit var item: OrderDetailItem
  private var init = true
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    appData.publicTools.setStatusAndNavBar(this)
    val orderId = intent.getStringExtra("orderId")!!
    init = true
    setContent {
      AppTheme {
        OrderDetailPage(
          colors = MaterialTheme.colorScheme,
          orderId = orderId
        )
      }
    }

  }

  data class OrderDetailItem(
    val amount: Int,
    val createTime: String,
    val date: String,
    val deptId: String,
    val deptName: String,
    val doctorAvatar: String,
    val doctorEmail: String,
    val doctorPhonenumber: String,
    val doctorRealName: String,
    val doctorSex: String,
    val offsetTime: Int,
    val orderId: String,
    val orderStatus: Int,
    val patientAge: Int,
    val patientCardId: String,
    val patientPhonenumber: String,
    val patientSex: String,
    val patientUserName: String,
    val title: String
  )

  @Composable
  fun OrderDetailPage(
    modifier: Modifier = Modifier,
    orderId: String,
    colors: ColorScheme = MaterialTheme.colorScheme
  ) {
    val progress = remember { mutableStateOf(false) }
    val decimalFormat = DecimalFormat("#0.00")
    if (init) {
      progress.value = true
      appData.netHelper.get(
        url = getString(R.string.admin_url)
            + "/api/service-order/order-info/getOrderByID?orderId="
            + orderId
      ) { data ->
        progress.value = false
        if (data == null) return@get
        val d = data.getJSONObject("data")
        item = OrderDetailItem(
          amount = d.getInt("amount"),
          createTime = d.getString("createTime"),
          date = d.getString("date"),
          deptId = d.getString("deptId"),
          deptName = d.getString("deptName"),
          doctorAvatar = d.getString("doctorAvatar"),
          doctorEmail = d.getString("doctorEmail"),
          doctorPhonenumber = d.getString("doctorPhonenumber"),
          doctorRealName = d.getString("doctorRealName"),
          doctorSex = d.getString("doctorSex"),
          offsetTime = d.getInt("offsetTime"),
          orderId = d.getString("orderId"),
          orderStatus = d.getInt("orderStatus"),
          patientAge = d.getInt("patientAge"),
          patientCardId = d.getString("patientCardId"),
          patientPhonenumber = d.getString("patientPhonenumber"),
          patientSex = d.getString("patientSex"),
          patientUserName = d.getString("patientUserName"),
          title = d.getString("title")
        )
      }
      init = false
    }

    Box(
      modifier = modifier
        .fillMaxSize()
        .background(colors.background)
    ) {
      if (progress.value)
        LoadingProgress()
      Column(
        modifier = modifier
          .fillMaxSize()
          .padding(10.dp),
      ) {
        TopBar(
          background = colors.background,
          backOnClick = { finish() },
          searchOnClick = {
            startActivity(
              Intent(
                this@OrderDetailActivity,
                SearchActivity::class.java
              )
            )
          }
        )
        Card(
          modifier = Modifier.padding(10.dp),
          colors = CardDefaults.cardColors(
            containerColor = colors.primaryContainer,
            contentColor = Color.Unspecified
          ),
          elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
          )
        )
        {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Icon(
              modifier = Modifier
                .clip(
                  shape = MaterialTheme.shapes.medium
                )
                .size(120.dp),
              painter = painterResource(id = R.drawable.usericon),
              contentDescription = null
            )
            Column(
              modifier = Modifier,
              verticalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = "姓名:" + item.patientUserName,
                fontWeight = FontWeight.Bold,
              )
              Spacer(Modifier.size(10.dp))
              Text(
                text = "年龄:" + item.patientAge.toString(),
                color = Color.Gray
              )
              Spacer(Modifier.size(10.dp))
              Text(
                text = "性别:" + if (item.patientSex == "0") "男" else "女",
                color = Color.Gray
              )
            }
            Spacer(modifier = Modifier.size(1.dp))
          }
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = 0.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
          ) {
            Column {
              Text(
                text = "电话号码:" + item.patientPhonenumber,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Gray
              )
              Spacer(Modifier.size(10.dp))
              Text(
                text = "身份证号:" + item.patientCardId,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Gray
              )
            }
          }
        }
        Card(
          modifier = Modifier.padding(10.dp),
          colors = CardDefaults.cardColors(
            containerColor = colors.primaryContainer,
            contentColor = Color.Unspecified
          ),
          elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
          )
        )
        {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "订单金额",
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "￥" + decimalFormat.format(item.amount),
              fontWeight = FontWeight.Bold
            )
          }
        }
        Card(
          modifier = Modifier.padding(10.dp),
          colors = CardDefaults.cardColors(
            containerColor = colors.primaryContainer,
            contentColor = Color.Unspecified
          ),
          elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
          )
        )
        {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text =
              "订单id:\n" + item.orderId + "\n\n"
                  + "订单创建时间:" + item.createTime + "\n\n"
                  + "医生姓名:" + item.doctorRealName + "\n\n"
                  + "医生性别:" + if (item.doctorSex == "0") {
                "男\n\n"
              } else {
                "女\n\n"

              }
                  + "医生部门:" + item.deptName + "\n\n"
                  + "医生职称:" + item.title + "\n\n"
                  + "医生电话:" + item.doctorPhonenumber + "\n\n"
                  + "医生邮箱:" + item.doctorEmail + "\n\n",
              fontSize = 14.sp,
              color = Color.Gray
            )
          }
        }

      }
    }

  }

  @Preview
  @Composable
  fun OrderDetailPagePreview() {
    item = OrderDetailItem(
      amount = 30,
      createTime = "2023-06-17 20:00:12",
      date = "27",
      deptId = "35",
      deptName = "肿瘤科普通门诊",
      doctorAvatar = "www.nichelle-cartwright.org",
      doctorEmail = "teodora.waters@gmail.com",
      doctorPhonenumber = "15926833215",
      doctorRealName = "邹立轩",
      doctorSex = "0",
      offsetTime = 2,
      orderId = "7ed45518-3421-45b4-b4db-ba2566198e50",
      orderStatus = 1,
      patientAge = 25,
      patientCardId = "122542",
      patientPhonenumber = "040240",
      patientSex = "0",
      patientUserName = "test3的病人1",
      title = "普通医生"
    )
    AppTheme {
      OrderDetailPage(
        colors = MaterialTheme.colorScheme,
        orderId = ""
      )
    }
  }
}