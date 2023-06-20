package edu.swu.xn.app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import edu.swu.xn.app.component.CommonSenseCard
import edu.swu.xn.app.component.OperateItem
import edu.swu.xn.app.component.VerticalIconButton
import edu.swu.xn.app.entity.CommonSense
import edu.swu.xn.app.entity.Operation
import edu.swu.xn.app.helper.AppData
import edu.swu.xn.app.ui.theme.AppTheme
import org.json.JSONObject

@SuppressLint("StaticFieldLeak")
lateinit var appData: AppData

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    appData = ViewModelProvider(this).get(AppData()::class.java)
    if (!appData.isInit) appData.init(this)
    appData.publicTools.setStatusAndNavBar(this)
    // 第一次使用
    if (appData.settings.getBoolean("FirstUse", true)) firstUse()
    // 检测登录
    appData.hashId = appData.settings.getString("hashId", "").toString()
    appData.userId = appData.settings.getInt("userId", 0)
    appData.userName = appData.settings.getString("userName", "").toString()

    if (appData.hashId == "") {
      startActivityForResult(
        Intent(
          this,
          LogInActivity::class.java
        ), 1
      )
    } else {
      setContent {
        AppTheme {
          HomePage(
            name = "小老弟",
            colors = MaterialTheme.colorScheme,
            commonSenses = listOf(
              CommonSense(
                imgUrl = "http://www.xnyy.cn/__local/7/80/AD/E112E1D2710394EDB1942A54571_900D5247_CBC5.png",
                url = "http://www.xnyy.cn/info/1031/19337.htm",
                title = "”凝心聚力，迎势启航”——2023年康复医学科责任组长竞聘活动及住培学员个人汇报总结大会",
                content = "为提高人员素质，更好地发挥责任组长在临床及管理中的骨干作用，同时将竞争机制全面引入临床管理中，更好地选拔、锻炼、培养骨干力量，优化队伍结构，打造合理的人才梯队。科室党支部支委会研究决定在全科开展2023年康复医学科科室护理组、治疗组及科研组的各组责任组长竞聘活动。本次活动采取“公平、公正、公开，鼓励优秀骨干积极参与”的原则，在科内择优选拔一批优秀的责任骨干，将协助科室强化临床管理、提高教学质量、推进科研人才培养，以此全面促进康复医学科的高质量发展。"
              ),
              CommonSense(
                imgUrl = "http://www.xnyy.cn/__local/7/80/AD/E112E1D2710394EDB1942A54571_900D5247_CBC5.png",
                url = "http://www.xnyy.cn/info/1031/19337.htm",
                title = "”凝心聚力，迎势启航”——2023年康复医学科责任组长竞聘活动及住培学员个人汇报总结大会",
                content = "为提高人员素质，更好地发挥责任组长在临床及管理中的骨干作用，同时将竞争机制全面引入临床管理中，更好地选拔、锻炼、培养骨干力量，优化队伍结构，打造合理的人才梯队。科室党支部支委会研究决定在全科开展2023年康复医学科科室护理组、治疗组及科研组的各组责任组长竞聘活动。本次活动采取“公平、公正、公开，鼓励优秀骨干积极参与”的原则，在科内择优选拔一批优秀的责任骨干，将协助科室强化临床管理、提高教学质量、推进科研人才培养，以此全面促进康复医学科的高质量发展。"
              ),
              CommonSense(
                imgUrl = "http://www.xnyy.cn/__local/7/80/AD/E112E1D2710394EDB1942A54571_900D5247_CBC5.png",
                url = "http://www.xnyy.cn/info/1031/19337.htm",
                title = "”凝心聚力，迎势启航”——2023年康复医学科责任组长竞聘活动及住培学员个人汇报总结大会",
                content = "为提高人员素质，更好地发挥责任组长在临床及管理中的骨干作用，同时将竞争机制全面引入临床管理中，更好地选拔、锻炼、培养骨干力量，优化队伍结构，打造合理的人才梯队。科室党支部支委会研究决定在全科开展2023年康复医学科科室护理组、治疗组及科研组的各组责任组长竞聘活动。本次活动采取“公平、公正、公开，鼓励优秀骨干积极参与”的原则，在科内择优选拔一批优秀的责任骨干，将协助科室强化临床管理、提高教学质量、推进科研人才培养，以此全面促进康复医学科的高质量发展。"
              ),
              CommonSense(
                imgUrl = "http://www.xnyy.cn/__local/7/80/AD/E112E1D2710394EDB1942A54571_900D5247_CBC5.png",
                url = "http://www.xnyy.cn/info/1031/19337.htm",
                title = "”凝心聚力，迎势启航”——2023年康复医学科责任组长竞聘活动及住培学员个人汇报总结大会",
                content = "为提高人员素质，更好地发挥责任组长在临床及管理中的骨干作用，同时将竞争机制全面引入临床管理中，更好地选拔、锻炼、培养骨干力量，优化队伍结构，打造合理的人才梯队。科室党支部支委会研究决定在全科开展2023年康复医学科科室护理组、治疗组及科研组的各组责任组长竞聘活动。本次活动采取“公平、公正、公开，鼓励优秀骨干积极参与”的原则，在科内择优选拔一批优秀的责任骨干，将协助科室强化临床管理、提高教学质量、推进科研人才培养，以此全面促进康复医学科的高质量发展。"
              ),
              CommonSense(
                imgUrl = "http://www.xnyy.cn/__local/7/80/AD/E112E1D2710394EDB1942A54571_900D5247_CBC5.png",
                url = "http://www.xnyy.cn/info/1031/19337.htm",
                title = "”凝心聚力，迎势启航”——2023年康复医学科责任组长竞聘活动及住培学员个人汇报总结大会",
                content = "为提高人员素质，更好地发挥责任组长在临床及管理中的骨干作用，同时将竞争机制全面引入临床管理中，更好地选拔、锻炼、培养骨干力量，优化队伍结构，打造合理的人才梯队。科室党支部支委会研究决定在全科开展2023年康复医学科科室护理组、治疗组及科研组的各组责任组长竞聘活动。本次活动采取“公平、公正、公开，鼓励优秀骨干积极参与”的原则，在科内择优选拔一批优秀的责任骨干，将协助科室强化临床管理、提高教学质量、推进科研人才培养，以此全面促进康复医学科的高质量发展。"
              ),
              CommonSense(
                imgUrl = "http://www.xnyy.cn/__local/7/80/AD/E112E1D2710394EDB1942A54571_900D5247_CBC5.png",
                url = "http://www.xnyy.cn/info/1031/19337.htm",
                title = "”凝心聚力，迎势启航”——2023年康复医学科责任组长竞聘活动及住培学员个人汇报总结大会",
                content = "为提高人员素质，更好地发挥责任组长在临床及管理中的骨干作用，同时将竞争机制全面引入临床管理中，更好地选拔、锻炼、培养骨干力量，优化队伍结构，打造合理的人才梯队。科室党支部支委会研究决定在全科开展2023年康复医学科科室护理组、治疗组及科研组的各组责任组长竞聘活动。本次活动采取“公平、公正、公开，鼓励优秀骨干积极参与”的原则，在科内择优选拔一批优秀的责任骨干，将协助科室强化临床管理、提高教学质量、推进科研人才培养，以此全面促进康复医学科的高质量发展。"
              ),
              CommonSense(
                imgUrl = "http://www.xnyy.cn/__local/7/80/AD/E112E1D2710394EDB1942A54571_900D5247_CBC5.png",
                url = "http://www.xnyy.cn/info/1031/19337.htm",
                title = "”凝心聚力，迎势启航”——2023年康复医学科责任组长竞聘活动及住培学员个人汇报总结大会",
                content = "为提高人员素质，更好地发挥责任组长在临床及管理中的骨干作用，同时将竞争机制全面引入临床管理中，更好地选拔、锻炼、培养骨干力量，优化队伍结构，打造合理的人才梯队。科室党支部支委会研究决定在全科开展2023年康复医学科科室护理组、治疗组及科研组的各组责任组长竞聘活动。本次活动采取“公平、公正、公开，鼓励优秀骨干积极参与”的原则，在科内择优选拔一批优秀的责任骨干，将协助科室强化临床管理、提高教学质量、推进科研人才培养，以此全面促进康复医学科的高质量发展。"
              ),
              CommonSense(
                imgUrl = "http://www.xnyy.cn/__local/7/80/AD/E112E1D2710394EDB1942A54571_900D5247_CBC5.png",
                url = "http://www.xnyy.cn/info/1031/19337.htm",
                title = "”凝心聚力，迎势启航”——2023年康复医学科责任组长竞聘活动及住培学员个人汇报总结大会",
                content = "为提高人员素质，更好地发挥责任组长在临床及管理中的骨干作用，同时将竞争机制全面引入临床管理中，更好地选拔、锻炼、培养骨干力量，优化队伍结构，打造合理的人才梯队。科室党支部支委会研究决定在全科开展2023年康复医学科科室护理组、治疗组及科研组的各组责任组长竞聘活动。本次活动采取“公平、公正、公开，鼓励优秀骨干积极参与”的原则，在科内择优选拔一批优秀的责任骨干，将协助科室强化临床管理、提高教学质量、推进科研人才培养，以此全面促进康复医学科的高质量发展。"
              )
            )
          )
        }
      }
    }
  }

  // 其他页面回调
  // 1为登录界面回调，
  @Deprecated("Deprecated in Java")
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    // 登录页面回调
    if (requestCode == 1) {
      if (resultCode == 1) {
        setContent {
          AppTheme {
            HomePage(
              name = "小老弟",
              commonSenses = listOf(
                CommonSense(
                  imgUrl = "http://www.xnyy.cn/__local/7/80/AD/E112E1D2710394EDB1942A54571_900D5247_CBC5.png",
                  url = "http://www.xnyy.cn/info/1031/19337.htm",
                  title = "”凝心聚力，迎势启航”——2023年康复医学科责任组长竞聘活动及住培学员个人汇报总结大会",
                  content = "为提高人员素质，更好地发挥责任组长在临床及管理中的骨干作用，同时将竞争机制全面引入临床管理中，更好地选拔、锻炼、培养骨干力量，优化队伍结构，打造合理的人才梯队。科室党支部支委会研究决定在全科开展2023年康复医学科科室护理组、治疗组及科研组的各组责任组长竞聘活动。本次活动采取“公平、公正、公开，鼓励优秀骨干积极参与”的原则，在科内择优选拔一批优秀的责任骨干，将协助科室强化临床管理、提高教学质量、推进科研人才培养，以此全面促进康复医学科的高质量发展。"
                ),
                CommonSense(
                  imgUrl = "http://www.xnyy.cn/__local/7/80/AD/E112E1D2710394EDB1942A54571_900D5247_CBC5.png",
                  url = "http://www.xnyy.cn/info/1031/19337.htm",
                  title = "”凝心聚力，迎势启航”——2023年康复医学科责任组长竞聘活动及住培学员个人汇报总结大会",
                  content = "为提高人员素质，更好地发挥责任组长在临床及管理中的骨干作用，同时将竞争机制全面引入临床管理中，更好地选拔、锻炼、培养骨干力量，优化队伍结构，打造合理的人才梯队。科室党支部支委会研究决定在全科开展2023年康复医学科科室护理组、治疗组及科研组的各组责任组长竞聘活动。本次活动采取“公平、公正、公开，鼓励优秀骨干积极参与”的原则，在科内择优选拔一批优秀的责任骨干，将协助科室强化临床管理、提高教学质量、推进科研人才培养，以此全面促进康复医学科的高质量发展。"
                ),
                CommonSense(
                  imgUrl = "http://www.xnyy.cn/__local/7/80/AD/E112E1D2710394EDB1942A54571_900D5247_CBC5.png",
                  url = "http://www.xnyy.cn/info/1031/19337.htm",
                  title = "”凝心聚力，迎势启航”——2023年康复医学科责任组长竞聘活动及住培学员个人汇报总结大会",
                  content = "为提高人员素质，更好地发挥责任组长在临床及管理中的骨干作用，同时将竞争机制全面引入临床管理中，更好地选拔、锻炼、培养骨干力量，优化队伍结构，打造合理的人才梯队。科室党支部支委会研究决定在全科开展2023年康复医学科科室护理组、治疗组及科研组的各组责任组长竞聘活动。本次活动采取“公平、公正、公开，鼓励优秀骨干积极参与”的原则，在科内择优选拔一批优秀的责任骨干，将协助科室强化临床管理、提高教学质量、推进科研人才培养，以此全面促进康复医学科的高质量发展。"
                ),
                CommonSense(
                  imgUrl = "http://www.xnyy.cn/__local/7/80/AD/E112E1D2710394EDB1942A54571_900D5247_CBC5.png",
                  url = "http://www.xnyy.cn/info/1031/19337.htm",
                  title = "”凝心聚力，迎势启航”——2023年康复医学科责任组长竞聘活动及住培学员个人汇报总结大会",
                  content = "为提高人员素质，更好地发挥责任组长在临床及管理中的骨干作用，同时将竞争机制全面引入临床管理中，更好地选拔、锻炼、培养骨干力量，优化队伍结构，打造合理的人才梯队。科室党支部支委会研究决定在全科开展2023年康复医学科科室护理组、治疗组及科研组的各组责任组长竞聘活动。本次活动采取“公平、公正、公开，鼓励优秀骨干积极参与”的原则，在科内择优选拔一批优秀的责任骨干，将协助科室强化临床管理、提高教学质量、推进科研人才培养，以此全面促进康复医学科的高质量发展。"
                ),
                CommonSense(
                  imgUrl = "http://www.xnyy.cn/__local/7/80/AD/E112E1D2710394EDB1942A54571_900D5247_CBC5.png",
                  url = "http://www.xnyy.cn/info/1031/19337.htm",
                  title = "”凝心聚力，迎势启航”——2023年康复医学科责任组长竞聘活动及住培学员个人汇报总结大会",
                  content = "为提高人员素质，更好地发挥责任组长在临床及管理中的骨干作用，同时将竞争机制全面引入临床管理中，更好地选拔、锻炼、培养骨干力量，优化队伍结构，打造合理的人才梯队。科室党支部支委会研究决定在全科开展2023年康复医学科科室护理组、治疗组及科研组的各组责任组长竞聘活动。本次活动采取“公平、公正、公开，鼓励优秀骨干积极参与”的原则，在科内择优选拔一批优秀的责任骨干，将协助科室强化临床管理、提高教学质量、推进科研人才培养，以此全面促进康复医学科的高质量发展。"
                ),
                CommonSense(
                  imgUrl = "http://www.xnyy.cn/__local/7/80/AD/E112E1D2710394EDB1942A54571_900D5247_CBC5.png",
                  url = "http://www.xnyy.cn/info/1031/19337.htm",
                  title = "”凝心聚力，迎势启航”——2023年康复医学科责任组长竞聘活动及住培学员个人汇报总结大会",
                  content = "为提高人员素质，更好地发挥责任组长在临床及管理中的骨干作用，同时将竞争机制全面引入临床管理中，更好地选拔、锻炼、培养骨干力量，优化队伍结构，打造合理的人才梯队。科室党支部支委会研究决定在全科开展2023年康复医学科科室护理组、治疗组及科研组的各组责任组长竞聘活动。本次活动采取“公平、公正、公开，鼓励优秀骨干积极参与”的原则，在科内择优选拔一批优秀的责任骨干，将协助科室强化临床管理、提高教学质量、推进科研人才培养，以此全面促进康复医学科的高质量发展。"
                ),
                CommonSense(
                  imgUrl = "http://www.xnyy.cn/__local/7/80/AD/E112E1D2710394EDB1942A54571_900D5247_CBC5.png",
                  url = "http://www.xnyy.cn/info/1031/19337.htm",
                  title = "”凝心聚力，迎势启航”——2023年康复医学科责任组长竞聘活动及住培学员个人汇报总结大会",
                  content = "为提高人员素质，更好地发挥责任组长在临床及管理中的骨干作用，同时将竞争机制全面引入临床管理中，更好地选拔、锻炼、培养骨干力量，优化队伍结构，打造合理的人才梯队。科室党支部支委会研究决定在全科开展2023年康复医学科科室护理组、治疗组及科研组的各组责任组长竞聘活动。本次活动采取“公平、公正、公开，鼓励优秀骨干积极参与”的原则，在科内择优选拔一批优秀的责任骨干，将协助科室强化临床管理、提高教学质量、推进科研人才培养，以此全面促进康复医学科的高质量发展。"
                ),
                CommonSense(
                  imgUrl = "http://www.xnyy.cn/__local/7/80/AD/E112E1D2710394EDB1942A54571_900D5247_CBC5.png",
                  url = "http://www.xnyy.cn/info/1031/19337.htm",
                  title = "”凝心聚力，迎势启航”——2023年康复医学科责任组长竞聘活动及住培学员个人汇报总结大会",
                  content = "为提高人员素质，更好地发挥责任组长在临床及管理中的骨干作用，同时将竞争机制全面引入临床管理中，更好地选拔、锻炼、培养骨干力量，优化队伍结构，打造合理的人才梯队。科室党支部支委会研究决定在全科开展2023年康复医学科科室护理组、治疗组及科研组的各组责任组长竞聘活动。本次活动采取“公平、公正、公开，鼓励优秀骨干积极参与”的原则，在科内择优选拔一批优秀的责任骨干，将协助科室强化临床管理、提高教学质量、推进科研人才培养，以此全面促进康复医学科的高质量发展。"
                )
              )
            )
          }
        }
      }
    }
    super.onActivityResult(requestCode, resultCode, data)
  }

  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun HomePage(
    modifier: Modifier = Modifier,
    name: String = "",
    fontSize: Int = 20,
    icon: Painter = painterResource(id = R.drawable.usericon),
    iconSize: Dp = 48.dp,
    searchOnClick: () -> Unit = {
      startActivity(Intent(this, SearchActivity::class.java))
    },
    messageOnClick: () -> Unit = {},
    operations: List<Operation> = listOf(
      Operation("挂号", painterResource(id = R.drawable.register)) {
        startActivity(Intent(this, SubjectActivity::class.java))
      },
      Operation("缴费", painterResource(id = R.drawable.pay)) {
        val intent = Intent(this, OrderActivity::class.java)
        intent.putExtra("for", "needPaymentOrderList")
        startActivity(intent)
      },
      Operation("报告单", painterResource(id = R.drawable.report)) {

      },
      Operation("订单", painterResource(id = R.drawable.order)) {
        val intent = Intent(this, OrderActivity::class.java)
        intent.putExtra("for", "fullOrderList")
        startActivity(intent)
      },
      Operation("智能导诊", painterResource(id = R.drawable.intelligent_guidance)) {
        startActivity(Intent(this, SubjectActivity::class.java))
      },
      Operation("待办", painterResource(id = R.drawable.pending)) {

      }
    ),
    commonSenses: List<CommonSense> = listOf(),
    colors: ColorScheme = MaterialTheme.colorScheme
  ) {
    /* 消息数状态 */
    val badgeNumber by rememberSaveable {
      mutableStateOf(8)
    }
    val visitorCount = remember { mutableStateOf(0) }

    val obj = JSONObject()
    obj.put("userID", appData.userId)
    appData.netHelper.get(
      url = stringResource(R.string.admin_url) + "/api/service-user/patient/getPatientInfo",
      value = obj
    )
    { data ->
      if (data == null) return@get
      val dataList = data.getJSONArray("data")
      visitorCount.value = dataList.length()
    }

    Box(
      modifier = modifier
        .background(colors.background)
        .fillMaxSize()
    )
    {
      /* 顶部背景椭圆 */
      Canvas(
        modifier = Modifier
          .fillMaxSize()
      ) {
        scale(scaleX = 15f, scaleY = 10f) {
          drawCircle(
            color = colors.background,
            radius = 40.dp.toPx(),
            center = this.center + Offset(0f, -160f)
          )
        }
      }
      /* 内容 */
      LazyColumn(
        modifier = Modifier
          .fillMaxSize(),
      ) {
        /* 顶部欢迎栏 */
        item {
          Row(
            modifier = Modifier
              .background(colors.background),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Spacer(modifier = Modifier.size(1.dp))
            // 右侧
            Row(
              modifier = Modifier
                .background(Color.Transparent)
                .weight(0.5f)
                .padding(
                  end = 20.dp
                ),
              horizontalArrangement = Arrangement.End
            ) {
              IconButton(onClick = searchOnClick) {
                Icon(
                  imageVector = Icons.Filled.Search,
                  contentDescription = null,
                  modifier = Modifier.size(20.dp),
                  tint = colorResource(id = R.color.iconColor)
                )
              }
              IconButton(
                onClick = messageOnClick
              ) {
                Icon(
                  imageVector = Icons.Default.Email,
                  contentDescription = null,
                  modifier = Modifier.size(20.dp),
                  tint = colorResource(id = R.color.iconColor)
                )
                if (badgeNumber > 0) {
                  Badge(
                    modifier = Modifier.padding(
                      bottom = 15.dp,
                      start = 15.dp
                    )
                  ) {
                    Text(
                      badgeNumber.toString(),
                      modifier = Modifier.semantics {
                        contentDescription =
                          "$badgeNumber new notifications"
                      }
                    )
                  }
                }
              }

            }
          }
          Row(
            modifier = Modifier
              .background(Color.Transparent)
              .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            // 左侧
            Row(
              modifier = Modifier
                .background(Color.Transparent)
                .weight(0.5f)
                .padding(start = 20.dp)
            ) {
              Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier
                  .size(iconSize)
                  .clip(CircleShape),
                tint = Color.Unspecified
              )
              Spacer(modifier = Modifier.size(8.dp))
              Column {
                Text(
                  text = "Hi $name,",
                  fontSize = fontSize.sp,
                  color = Color.Black,
                  fontWeight = FontWeight.Bold
                )
                Text(
                  text = "欢迎回来！",
                  fontSize = (fontSize - 2).sp,
                  color = Color.LightGray
                )
              }
            }
            Spacer(modifier = Modifier.size(1.dp))
          }
          Spacer(modifier = Modifier.size(20.dp))
        }
        /* 病历 + 人 */
        item {
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(20.dp),
            colors = CardDefaults.cardColors(
              containerColor = colors.primaryContainer
            ),
            elevation = CardDefaults.cardElevation(
              defaultElevation = 4.dp
            ),
            onClick = {
              startActivity(Intent(appData.main, VisitorActivity::class.java))
            },
            shape = MaterialTheme.shapes.extraLarge
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              VerticalIconButton(
                text = "就诊人",
                icon = painterResource(id = R.drawable.patient_manage),
                iconSize = 40.dp,
              )
              Text(
                "${visitorCount.value}人",
                modifier = Modifier.padding(20.dp)
              )
            }
          }
        }
        /* 卡片操作栏 */
        item {
          Card(
            modifier = Modifier
              .padding(
                start = 20.dp,
                end = 20.dp
              )
              .height(306.dp),
            colors = CardDefaults.cardColors(
              containerColor = colors.primaryContainer,
            ),
            elevation = CardDefaults.cardElevation(
              defaultElevation = 4.dp
            )
          ) {
            LazyVerticalGrid(
              columns = GridCells.Fixed(2),
              contentPadding = PaddingValues(20.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              userScrollEnabled = false
            )
            {
              items(operations.size) {
                OperateItem(
                  text = operations[it].text,
                  icon = operations[it].icon,
                  onClick = operations[it].onClick,
                  iconSize = 50.dp
                )
              }
            }
          }
          Spacer(modifier = Modifier.size(10.dp))
        }
        /* 常识列表 */
        items(commonSenses.size) { index ->
          CommonSenseCard(
            modifier = Modifier.background(colors.background),
            commonSense = commonSenses[index],
            containerColor = colors.primaryContainer,
            onClick = {
              val intent = Intent(this@MainActivity, ArticleActivity::class.java)
              intent.putExtra(
                "url", commonSenses[index].url
              )
              startActivity(intent)
            }
          )
        }
      }
    }
  }

  @Preview
  @Composable
  fun HomePagePreview() {
    AppTheme {
      HomePage(
        name = "小老弟",
        colors = MaterialTheme.colorScheme,
        commonSenses = listOf(
          CommonSense(
            imgUrl = "http://www.xnyy.cn/__local/7/80/AD/E112E1D2710394EDB1942A54571_900D5247_CBC5.png",
            url = "http://www.xnyy.cn/info/1031/19337.htm",
            title = "”凝心聚力，迎势启航”——2023年康复医学科责任组长竞聘活动及住培学员个人汇报总结大会",
            content = "为提高人员素质，更好地发挥责任组长在临床及管理中的骨干作用，同时将竞争机制全面引入临床管理中，更好地选拔、锻炼、培养骨干力量，优化队伍结构，打造合理的人才梯队。科室党支部支委会研究决定在全科开展2023年康复医学科科室护理组、治疗组及科研组的各组责任组长竞聘活动。本次活动采取“公平、公正、公开，鼓励优秀骨干积极参与”的原则，在科内择优选拔一批优秀的责任骨干，将协助科室强化临床管理、提高教学质量、推进科研人才培养，以此全面促进康复医学科的高质量发展。"
          ),
          CommonSense(
            imgUrl = "http://www.xnyy.cn/__local/7/80/AD/E112E1D2710394EDB1942A54571_900D5247_CBC5.png",
            url = "http://www.xnyy.cn/info/1031/19337.htm",
            title = "”凝心聚力，迎势启航”——2023年康复医学科责任组长竞聘活动及住培学员个人汇报总结大会",
            content = "为提高人员素质，更好地发挥责任组长在临床及管理中的骨干作用，同时将竞争机制全面引入临床管理中，更好地选拔、锻炼、培养骨干力量，优化队伍结构，打造合理的人才梯队。科室党支部支委会研究决定在全科开展2023年康复医学科科室护理组、治疗组及科研组的各组责任组长竞聘活动。本次活动采取“公平、公正、公开，鼓励优秀骨干积极参与”的原则，在科内择优选拔一批优秀的责任骨干，将协助科室强化临床管理、提高教学质量、推进科研人才培养，以此全面促进康复医学科的高质量发展。"
          ),
          CommonSense(
            imgUrl = "http://www.xnyy.cn/__local/7/80/AD/E112E1D2710394EDB1942A54571_900D5247_CBC5.png",
            url = "http://www.xnyy.cn/info/1031/19337.htm",
            title = "”凝心聚力，迎势启航”——2023年康复医学科责任组长竞聘活动及住培学员个人汇报总结大会",
            content = "为提高人员素质，更好地发挥责任组长在临床及管理中的骨干作用，同时将竞争机制全面引入临床管理中，更好地选拔、锻炼、培养骨干力量，优化队伍结构，打造合理的人才梯队。科室党支部支委会研究决定在全科开展2023年康复医学科科室护理组、治疗组及科研组的各组责任组长竞聘活动。本次活动采取“公平、公正、公开，鼓励优秀骨干积极参与”的原则，在科内择优选拔一批优秀的责任骨干，将协助科室强化临床管理、提高教学质量、推进科研人才培养，以此全面促进康复医学科的高质量发展。"
          ),
          CommonSense(
            imgUrl = "http://www.xnyy.cn/__local/7/80/AD/E112E1D2710394EDB1942A54571_900D5247_CBC5.png",
            url = "http://www.xnyy.cn/info/1031/19337.htm",
            title = "”凝心聚力，迎势启航”——2023年康复医学科责任组长竞聘活动及住培学员个人汇报总结大会",
            content = "为提高人员素质，更好地发挥责任组长在临床及管理中的骨干作用，同时将竞争机制全面引入临床管理中，更好地选拔、锻炼、培养骨干力量，优化队伍结构，打造合理的人才梯队。科室党支部支委会研究决定在全科开展2023年康复医学科科室护理组、治疗组及科研组的各组责任组长竞聘活动。本次活动采取“公平、公正、公开，鼓励优秀骨干积极参与”的原则，在科内择优选拔一批优秀的责任骨干，将协助科室强化临床管理、提高教学质量、推进科研人才培养，以此全面促进康复医学科的高质量发展。"
          )
        )
      )
    }
  }


  // 第一次使用
  private fun firstUse() {
    // 通知注册
    appData.notificationHelper.registerChannel()
    // 权限申请
    appData.notificationHelper.requireNotificationPermission()
    // 保存
    appData.settings.edit().apply {
      putBoolean("FirstUse", false)
      apply()
    }
  }
}