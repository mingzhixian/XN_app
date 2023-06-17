package edu.swu.xn.app

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.swu.xn.app.ui.theme.AppTheme

class PayActivity : AppCompatActivity() {
  private lateinit var orderID:String
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    orderID = intent.getStringExtra("orderId")!!
    setContent {
      AppTheme {
        PayPage()
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
    context: Context = this,
  ) {
    val payItems: List<PayItem> = rememberSaveable {
      mutableListOf<PayItem>(
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

    var ok_icon by remember { mutableStateOf(R.drawable.wechat) }
    var isOK by remember { mutableStateOf(false) }

    var colors = MaterialTheme.colorScheme
    AppTheme {
      colors = MaterialTheme.colorScheme
    }

    /* 顶部背景椭圆 */
    Canvas(
      modifier = modifier
        .fillMaxSize()
        .background(colors.background)
    ) {
      scale(scaleX = 15f, scaleY = 10f) {
        drawCircle(
          color = colors.primaryContainer,
          radius = 40.dp.toPx(),
          center = this.center + Offset(0f, -200f)
        )
      }
    }
    Box {
      LazyColumn(
        modifier = modifier
          .fillMaxSize()
      ) {
        item {
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
              ok_icon = payItems[index].iconID
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
      if (isOK) {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(40.dp),
          elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
          ),
          colors = CardDefaults.cardColors(
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
                .padding(top = 80.dp)
                .size(100.dp),
              painter = painterResource(id = ok_icon),
              contentDescription = null,
            )
            Text(
              modifier = Modifier.padding(
                top = 20.dp,
                bottom = 10.dp
              ),
              text = "支付成功",
              fontSize = 28.sp,
              color = Color.DarkGray
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
    PayPage()
  }

}

