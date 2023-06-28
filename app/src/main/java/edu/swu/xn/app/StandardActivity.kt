package edu.swu.xn.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.swu.xn.app.component.TopBar

class StandardActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    appData.publicTools.setStatusAndNavBar(this)
    setContent {
      StandardPage()
    }
  }

  data class Item(
    val title: String,
    val content: String
  )

  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun StandardPage(
    itemlist: List<Item> = listOf<Item>(
      Item(
        "正常心率：每分钟75次",
        "健康成年人安静状态下，心率平均为每分钟75次。正常范围为每分钟60-100次。成人安静时心率超过100次/分钟，为心动过速；低于60次/分钟者，为心动过缓。心率可因年龄、性别及其他因素而变化，比如体温每升高1℃，心率可加快12-20次/分钟，女性心率比男性心率稍快，运动员的心率较慢。"
      ),
      Item(
        "正常体温：36.3℃-37.2℃（口测法）",
        "临床上通常用口腔温度、直肠温度和腋窝温度来代表体温。口测法（舌下含5分钟）正常值为36.3℃-37.2℃；腋测法（腋下夹紧5分钟）为36℃-37℃；肛测法（表头涂润滑剂，插入肛门5分钟）为36.5℃-37.7℃。在一昼夜中，人体体温呈周期性波动，一般清晨2-6时最低，下午13-18时最高，但波动幅度一般不超过1℃。只要体温不超过37.3℃，就算正常。"
      ),
      Item(
        "血红蛋白（HbB）：成年男性（120-160克/升），成年女性（110-150克/升）",
        "临床上以血红蛋白值佐为判断贫血的依据。 正常成人血红蛋白值90-110克/升属轻度贫血；60-90克/升属中度贫血；30-60克/升属重度贫血。"
      ),
      Item(
        "白细胞计数（WBC）：4-10*（10的9次方）个/升",
        "白细胞计数大于10*（10的9次方）个/升称白细胞增多，小于10*（10的9次方）个/升称白细胞减少。一般地说，急性细菌感染或炎症时，白细胞可升高；病毒感染时，白细胞会降低。感冒、发热可由病毒感染引起，也可由细菌感染引起，为明确病因，指导临床用药，医生通常会让你去查一个血常规。"
      ),
      Item(
        "血小板计数（PLT）：100-300*（10的9次方）个/升 ",
        "血小板有维护血管壁完整性的功能。当血小板数减少到50*（10的9次方）个/升以下时，特别是低至30*（10的9次方）个/升时，就有可能导致出血，皮肤上可出现瘀点瘀斑。血小板不低皮肤上也常出现“乌青块”者不必过分紧张，因为除了血小板因素外，血管壁因素，凝血因素，以及一些生理性因素都会导致“乌青块”的发生，可去血液科就诊，明确原因。"
      ),

      )
  ) {
    LazyColumn(
      modifier = Modifier.background(Color(getColor(R.color.background)))
    ) {
      item {
        TopBar(
          background = Color(getColor(R.color.background)),
          text = "医学常识",
          backOnClick = { finish() },
          searchOnClick = {
            startActivity(
              Intent(
                this@StandardActivity,
                SearchActivity::class.java
              )
            )
          }
        )
      }
      items(itemlist.size) { index ->
        Card(
          modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
          colors = CardDefaults.cardColors(
            containerColor = Color(getColor(R.color.cardBackground))
          ),
          elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
          ),
        ) {
          Row(
            modifier = Modifier.padding(
              start = 15.dp,
              end = 15.dp,
              top = 10.dp,
              bottom = 10.dp
            )
          ) {
            Column {
              Text(
                text = itemlist[index].title,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
              )
              Spacer(modifier = Modifier.size(4.dp))
              Text(
                text = itemlist[index].content,
                color = Color.Gray,
                fontSize = 14.sp,
              )
            }
          }
        }
      }
    }
  }

  @Preview
  @Composable
  fun StandardPagePreview() {
    StandardPage()
  }

}

