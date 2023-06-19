package edu.swu.xn.app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.swu.xn.app.component.LoadingProgress
import edu.swu.xn.app.component.TopBar
import edu.swu.xn.app.component.TopRoundBackground
import edu.swu.xn.app.ui.theme.AppTheme
import org.json.JSONObject
import java.text.DecimalFormat

class PayActivity : AppCompatActivity() {
  private lateinit var orderID: String
  private var amount: Float = 0f
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    appData.publicTools.setStatusAndNavBar(this)
    orderID = intent.extras?.getString("orderId")!!
    amount = intent.extras!!.getString("amount")!!.toFloat()
    setContent {
      AppTheme {
        PayPage(colors = MaterialTheme.colorScheme)
      }
    }
  }

  data class PayItem(
    val payType: String,
    val iconID: Int,
  )

  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun PayPage(
    modifier: Modifier = Modifier,
    colors: ColorScheme = MaterialTheme.colorScheme
  ) {
    val payItems: List<PayItem> = rememberSaveable {
      mutableListOf(
        PayItem(
          payType = "微信支付",
          iconID = R.drawable.wechat
        ),
        PayItem(
          payType = "支付宝支付",
          iconID = R.drawable.alipay
        ),
        PayItem(
          payType = "银行卡支付 **** **** **** 1234",
          iconID = R.drawable.bankcard
        )
      )
    }

    var okIcon by remember { mutableStateOf(R.drawable.wechat) }
    var isOK by remember { mutableStateOf(false) }

    val amount by remember { mutableStateOf(amount) }
    val decimalFormat = DecimalFormat("#0.00")

    val progress = remember { mutableStateOf(false) }


    /* 顶部背景椭圆 */
    TopRoundBackground(
      backgroundColor = colors.background,
      containerColor = colors.primary,
      offset = -200f
    )

    Box {
      if (progress.value)
        LoadingProgress()

      LazyColumn(
        modifier = modifier
          .fillMaxSize()
      ) {
        /* 标题文本 */
        item {
          TopBar(
            background = colors.background,
            backOnClick = { finish() },
            searchOnClick = { startActivity(Intent(this@PayActivity, SearchActivity::class.java)) }
          )
          Text(
            modifier = Modifier.padding(
              start = 10.dp
            ),
            text = "选择支付方式",
            fontSize = 28.sp,
            color = Color.DarkGray
          )
          Text(
            modifier = Modifier.padding(
              start = 15.dp
            ),
            text = "选择您医院就诊的支付方式",
            fontSize = 20.sp,
            color = Color.Gray
          )
          Spacer(
            modifier = Modifier.size(50.dp)
          )
        }
        /* 支付金额 */
        item {
          Text(
            text = "支付金额",
            modifier = Modifier.padding(
              start = 30.dp
            ),
            color = Color.Gray
          )
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
          ) {
            Text(
              text = "￥",
              fontSize = 30.sp,
              fontWeight = FontWeight.ExtraBold
            )
            Text(
              modifier = Modifier.padding(
                bottom = 50.dp
              ),
              text = decimalFormat.format(amount),
              fontSize = 40.sp,
              fontWeight = FontWeight.ExtraBold
            )
          }
        }
        /* 支付方式 */
        items(payItems.size) { index ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(
                start = 10.dp,
                end = 10.dp,
                top = 5.dp,
              ),
            colors = CardDefaults.cardColors(
              contentColor = Color.Unspecified
            ),
            onClick = {
              okIcon = payItems[index].iconID
              progress.value = true
              /* 请求支付订单 */
              val obj = JSONObject()
              obj.put("orderId", orderID)
              appData.netHelper.get(
                url = appData.main.getString(R.string.admin_url) + "/api/service-order/order-info/payOrder",
                value = obj
              ) { data ->
                if (data.getString("code") == "200") {
                  Toast.makeText(this@PayActivity, "支付成功", Toast.LENGTH_LONG).show()
                } else {
                  Toast.makeText(this@PayActivity, "支付失败", Toast.LENGTH_LONG).show()
                }
                progress.value = false
              }

              isOK = true
            }
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                modifier = Modifier
                  .padding(20.dp)
                  .size(40.dp),
                painter = painterResource(id = payItems[index].iconID),
                contentDescription = null
              )
              Text(
                text = payItems[index].payType,
              )
              Icon(
                modifier = Modifier
                  .padding(20.dp)
                  .size(40.dp),
                painter = painterResource(id = R.drawable.radio),
                contentDescription = null,
              )
            }

          }
        }
      }

      /* 支付成功卡片 */
      if (isOK) {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
              top = 150.dp,
              bottom = 100.dp,
              start = 40.dp,
              end = 40.dp
            ),
          elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
          ),
          colors = CardDefaults.cardColors(
            containerColor = colors.background,
            contentColor = Color.Unspecified
          )
        ) {
          Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(
              modifier = Modifier
                .padding(top = 40.dp)
                .size(80.dp),
              painter = painterResource(id = okIcon),
              contentDescription = null,
            )
            Text(
              modifier = Modifier.padding(
                top = 20.dp,
                bottom = 10.dp
              ),
              text = "支付成功",
              fontSize = 28.sp,
              color = Color.DarkGray,
              fontWeight = FontWeight.Bold
            )
            Text(
              modifier = Modifier.padding(
                top = 10.dp,
                bottom = 10.dp
              ),
              text = "请谨慎保存支付凭证哦~",
              fontSize = 18.sp,
              color = Color.Gray
            )
            Spacer(modifier = Modifier.size(40.dp))
            Button(
              onClick = {
                isOK = false
                finish()
              },
              modifier = Modifier
                .fillMaxWidth()
                .padding(
                  start = 50.dp,
                  end = 50.dp,
                  bottom = 80.dp
                )
                .height(60.dp)
            ) {
              Text(text = "好的")
            }
          }
        }
      }
    }
  }


  @Preview
  @Composable
  fun PayPagePreview() {
    AppTheme {
      PayPage(colors = MaterialTheme.colorScheme)
    }
  }

}

