package edu.swu.xn.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.swu.xn.app.entity.Visitor
import edu.swu.xn.app.ui.theme.AppTheme
import org.json.JSONObject

class VisitorActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisitorPage(
  modifier: Modifier = Modifier
) {
  var colors = MaterialTheme.colorScheme
  AppTheme {
    colors = MaterialTheme.colorScheme
  }

  var visitorList: List<Visitor>
  val obj = JSONObject()
  obj.put("userID", appData.hashID)
  appData.netHelper.get(stringResource(R.string.admin_url) + "/api/service-user/patient/getPatientInfo")
  {
  }

  var visitors = remember {
    mutableStateListOf<Visitor>(
      Visitor(
        name = "病人1",
        id = 1234,
        sex = "男",
        phoneNumber = "110",
        medicalHistory = "手臂曾动过大型手术",
        cardID = "5002231558415",
        age = 15
      ),
      Visitor(
        name = "病人1",
        id = 1234,
        sex = "男",
        phoneNumber = "110",
        medicalHistory = "手臂曾动过大型手术",
        cardID = "5002231558415",
        age = 15
      ),
      Visitor(
        name = "病人1",
        id = 1234,
        sex = "男",
        phoneNumber = "110",
        medicalHistory = "手臂曾动过大型手术",
        cardID = "5002231558415",
        age = 15
      )
    )
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
  LazyColumn(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
  )
  {
    item {
      Text(
        modifier = Modifier.padding(
          top = 40.dp,
          bottom = 40.dp
        ),
        text = "就诊人",
        fontSize = 28.sp,
        color = Color.DarkGray
      )
    }
    items(visitors.size) { index ->
      Card(
        modifier = modifier
          .padding(10.dp)
          .fillMaxWidth(),
        colors = CardDefaults.cardColors(
          containerColor = colors.background,
          contentColor = Color.Unspecified
        ),
        elevation = CardDefaults.cardElevation(
          defaultElevation = 4.dp
        ),
        onClick = { }
      ) {
        Row(
          modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Icon(
            modifier = Modifier
              .size(100.dp)
              .clip(RoundedCornerShape(50)),
            painter = painterResource(id = R.drawable.usericon),
            contentDescription = null
          )
          Column {
            Text(
              modifier = Modifier.padding(
                start = 30.dp,
                bottom = 2.dp
              ),
              text = "姓名:" + visitors[index].name,
              fontSize = 18.sp,
              fontWeight = FontWeight.SemiBold
            )
            Text(
              modifier = Modifier.padding(
                start = 30.dp,
                bottom = 2.dp
              ),
              text = "性别:" + visitors[index].sex,
              fontSize = 14.sp,
              color = Color.Gray
            )
            Text(
              modifier = Modifier.padding(
                start = 30.dp,
                bottom = 2.dp
              ),
              text = "年龄:" + visitors[index].age.toString() + "岁",
              fontSize = 14.sp,
              color = Color.Gray
            )
            Text(
              modifier = Modifier.padding(
                start = 30.dp
              ),
              text = "电话号码: ${visitors[index].phoneNumber}",
              fontSize = 12.sp,
              color = Color.Gray
            )
          }
        }
      }
    }

    item {
      Button(
        modifier = Modifier
          .fillMaxWidth()
          .padding(
            top = 30.dp,
            start = 40.dp,
            end = 40.dp
          )
          .border(
            width = 1.dp,
            color = Color.LightGray,
            shape = MaterialTheme.shapes.medium
          ),
        onClick = {},
        colors = ButtonDefaults.buttonColors(
          containerColor = Color.Transparent,
          contentColor = colors.onBackground
        )
      ) {
        Text("添加就诊人")
        Icon(
          imageVector = Icons.Filled.Add,
          contentDescription = null
        )

      }
    }
  }
}

@Preview
@Composable
fun VisitorPagePreview() {
  VisitorPage()
}
