package edu.swu.xn.app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
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

@SuppressLint("StaticFieldLeak")
lateinit var appData: AppData

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    appData = ViewModelProvider(this).get(AppData()::class.java)
    if (!appData.isInit) appData.init(this)
    // 第一次使用
    if (appData.settings.getBoolean("FirstUse", true)) firstUse()
    // 检测登录
    appData.hashID = appData.settings.getString("hashID", "").toString()
    if (appData.hashID == "") {
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
            commonSenses = listOf(
              CommonSense(
                url = "https://avatars.githubusercontent.com/u/78494317?s=40&v=4",
                title = "这是标题 Launching 'CommonSenseCardPreview' on HUAWEI ANA-AN00.",
                content = "这是内容 App restart successful without re-installing the following APK(s): edu.swu.xn.app.test"
              ),
              CommonSense(
                url = "https://avatars.githubusercontent.com/u/78494317?s=40&v=4",
                title = "这是标题 Launching 'CommonSenseCardPreview' on HUAWEI ANA-AN00.",
                content = "这是内容 App restart successful without re-installing the following APK(s): edu.swu.xn.app.test"
              ),
              CommonSense(
                url = "https://avatars.githubusercontent.com/u/78494317?s=40&v=4",
                title = "这是标题 Launching 'CommonSenseCardPreview' on HUAWEI ANA-AN00.",
                content = "这是内容 App restart successful without re-installing the following APK(s): edu.swu.xn.app.test"
              ),
              CommonSense(
                url = "https://avatars.githubusercontent.com/u/78494317?s=40&v=4",
                title = "这是标题 Launching 'CommonSenseCardPreview' on HUAWEI ANA-AN00.",
                content = "这是内容 App restart successful without re-installing the following APK(s): edu.swu.xn.app.test"
              ),
              CommonSense(
                url = "https://avatars.githubusercontent.com/u/78494317?s=40&v=4",
                title = "这是标题 Launching 'CommonSenseCardPreview' on HUAWEI ANA-AN00.",
                content = "这是内容 App restart successful without re-installing the following APK(s): edu.swu.xn.app.test"
              ),
              CommonSense(
                url = "https://avatars.githubusercontent.com/u/78494317?s=40&v=4",
                title = "这是标题 Launching 'CommonSenseCardPreview' on HUAWEI ANA-AN00.",
                content = "这是内容 App restart successful without re-installing the following APK(s): edu.swu.xn.app.test"
              ),
              CommonSense(
                url = "https://avatars.githubusercontent.com/u/78494317?s=40&v=4",
                title = "这是标题 Launching 'CommonSenseCardPreview' on HUAWEI ANA-AN00.",
                content = "这是内容 App restart successful without re-installing the following APK(s): edu.swu.xn.app.test"
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
                  url = "https://avatars.githubusercontent.com/u/78494317?s=40&v=4",
                  title = "这是标题 Launching 'CommonSenseCardPreview' on HUAWEI ANA-AN00.",
                  content = "这是内容 App restart successful without re-installing the following APK(s): edu.swu.xn.app.test"
                ),
                CommonSense(
                  url = "https://avatars.githubusercontent.com/u/78494317?s=40&v=4",
                  title = "这是标题 Launching 'CommonSenseCardPreview' on HUAWEI ANA-AN00.",
                  content = "这是内容 App restart successful without re-installing the following APK(s): edu.swu.xn.app.test"
                ),
                CommonSense(
                  url = "https://avatars.githubusercontent.com/u/78494317?s=40&v=4",
                  title = "这是标题 Launching 'CommonSenseCardPreview' on HUAWEI ANA-AN00.",
                  content = "这是内容 App restart successful without re-installing the following APK(s): edu.swu.xn.app.test"
                ),
                CommonSense(
                  url = "https://avatars.githubusercontent.com/u/78494317?s=40&v=4",
                  title = "这是标题 Launching 'CommonSenseCardPreview' on HUAWEI ANA-AN00.",
                  content = "这是内容 App restart successful without re-installing the following APK(s): edu.swu.xn.app.test"
                ),
                CommonSense(
                  url = "https://avatars.githubusercontent.com/u/78494317?s=40&v=4",
                  title = "这是标题 Launching 'CommonSenseCardPreview' on HUAWEI ANA-AN00.",
                  content = "这是内容 App restart successful without re-installing the following APK(s): edu.swu.xn.app.test"
                ),
                CommonSense(
                  url = "https://avatars.githubusercontent.com/u/78494317?s=40&v=4",
                  title = "这是标题 Launching 'CommonSenseCardPreview' on HUAWEI ANA-AN00.",
                  content = "这是内容 App restart successful without re-installing the following APK(s): edu.swu.xn.app.test"
                ),
                CommonSense(
                  url = "https://avatars.githubusercontent.com/u/78494317?s=40&v=4",
                  title = "这是标题 Launching 'CommonSenseCardPreview' on HUAWEI ANA-AN00.",
                  content = "这是内容 App restart successful without re-installing the following APK(s): edu.swu.xn.app.test"
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
    fontSize: Int = 14,
    icon: Painter = painterResource(id = R.drawable.usericon),
    iconSize: Dp = 48.dp,
    searchOnClick: () -> Unit = {},
    messageOnClick: () -> Unit = {},
    operations: List<Operation> = listOf(
      Operation("挂号", painterResource(id = R.drawable.register)) {
        startActivity(Intent(this, SubjectActivity::class.java))
      },
      Operation("缴费", painterResource(id = R.drawable.pay)) {

      },
      Operation("报告单", painterResource(id = R.drawable.report)) {

      },
      Operation("订单", painterResource(id = R.drawable.order)) {

      },
      Operation("智能导诊", painterResource(id = R.drawable.intelligent_guidance)) {

      },
      Operation("待办", painterResource(id = R.drawable.pending)) {

      }
    ),
    commonSenses: List<CommonSense> = listOf(),
  ) {
    var colors = MaterialTheme.colorScheme
    AppTheme {
      colors = MaterialTheme.colorScheme
    }
    /* 消息数状态 */
    var badgeNumber by rememberSaveable {
      mutableStateOf(0)
    }
    Box(
      modifier = modifier
        .background(Color.White)
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
            color = colors.primary,
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
              .background(Color.Transparent)
              .padding(top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            // 右侧
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
                  color = Color.White
                )
                Text(
                  text = "欢迎回来！",
                  fontSize = (fontSize - 2).sp,
                  color = Color.LightGray
                )
              }
            }
            // 左侧
            Row(
              modifier = Modifier
                .background(Color.Transparent)
                .weight(0.5f),
              horizontalArrangement = Arrangement.End
            ) {
              IconButton(onClick = searchOnClick) {
                Icon(
                  imageVector = Icons.Filled.Search,
                  contentDescription = null,
                  modifier = Modifier.size(20.dp),
                  tint = Color.White
                )
              }
              IconButton(onClick = messageOnClick) {
                Icon(
                  imageVector = Icons.Default.Email,
                  contentDescription = null,
                  modifier = Modifier.size(20.dp),
                  tint = Color.White
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
          Spacer(modifier = Modifier.size(20.dp))
        }
        /* 病历 + 人 */
        item {
          Row(
            modifier = Modifier.fillMaxWidth()
          ) {
            Card(
              modifier = Modifier
                .padding(20.dp)
                .weight(0.5f),
              colors = CardDefaults.cardColors(
                containerColor = colors.primaryContainer
              ),
              elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
              )
            ) {
              VerticalIconButton(
                text = "病历",
                icon = painterResource(id = R.drawable.medical_record),
                iconSize = 40.dp
              )
            }
            Card(
              modifier = Modifier
                .padding(20.dp)
                .weight(0.5f),
              colors = CardDefaults.cardColors(
                containerColor = colors.primaryContainer
              ),
              elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
              )
            ) {
              VerticalIconButton(
                text = "就诊人",
                icon = painterResource(id = R.drawable.patient),
                iconSize = 40.dp
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
              containerColor = colors.secondaryContainer,
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
            commonSense = commonSenses[index]
          )
        }
      }
    }
  }

  @Preview
  @Composable
  fun HomePagePreview() {
    HomePage(
      name = "小老弟",
      commonSenses = listOf(
        CommonSense(
          url = "https://avatars.githubusercontent.com/u/78494317?s=40&v=4",
          title = "这是标题 Launching 'CommonSenseCardPreview' on HUAWEI ANA-AN00.",
          content = "这是内容 App restart successful without re-installing the following APK(s): edu.swu.xn.app.test"
        ),
        CommonSense(
          url = "https://avatars.githubusercontent.com/u/78494317?s=40&v=4",
          title = "这是标题 Launching 'CommonSenseCardPreview' on HUAWEI ANA-AN00.",
          content = "这是内容 App restart successful without re-installing the following APK(s): edu.swu.xn.app.test"
        ),
        CommonSense(
          url = "https://avatars.githubusercontent.com/u/78494317?s=40&v=4",
          title = "这是标题 Launching 'CommonSenseCardPreview' on HUAWEI ANA-AN00.",
          content = "这是内容 App restart successful without re-installing the following APK(s): edu.swu.xn.app.test"
        ),
        CommonSense(
          url = "https://avatars.githubusercontent.com/u/78494317?s=40&v=4",
          title = "这是标题 Launching 'CommonSenseCardPreview' on HUAWEI ANA-AN00.",
          content = "这是内容 App restart successful without re-installing the following APK(s): edu.swu.xn.app.test"
        )
      )
    )
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