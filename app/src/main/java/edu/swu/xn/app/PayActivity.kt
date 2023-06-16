package edu.swu.xn.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.swu.xn.app.ui.theme.AppTheme

class PayActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
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

  @Composable
  fun PayPage(
    modifier: Modifier = Modifier
  ){
    val payItems: List<PayItem> = rememberSaveable {mutableListOf<PayItem>(
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
    )}

    LazyColumn(
      modifier = modifier
        .fillMaxSize()
        .background(Color.White)
    ){
      item {
        Text(
          modifier = Modifier.padding(
            start = 10.dp
          ),
          text = "选择支付方式",
          fontSize = 14.sp,
          color = Color.DarkGray
        )
        Text(
          modifier = Modifier.padding(
            start = 10.dp
          ),
          text = "选择您医院就诊的支付方式",
          fontSize = 12.sp,
          color = Color.Gray
        )
        Spacer(
          modifier = Modifier.size(10.dp)
        )
      }
      items(payItems.size) {index ->
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

  }


  @Preview
  @Composable
  fun PayPagePreview()
  {
    PayPage()
  }

}

