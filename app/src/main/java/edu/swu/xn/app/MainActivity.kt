package edu.swu.xn.app

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import edu.swu.xn.app.component.MyButton
import edu.swu.xn.app.entity.Operation
import edu.swu.xn.app.helper.AppData
import edu.swu.xn.app.ui.theme.XN_appTheme

lateinit var appData: AppData

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appData = ViewModelProvider(this).get(AppData::class.java)
        if (!appData.isInit) appData.init(this)
        // 第一次使用
        if (appData.settings.getBoolean("FirstUse", true)) firstUse()
        // 检测登录

        setContent {
            XN_appTheme {
                // A surface container using the 'background' color from the theme
                HomePage(name = "小老弟")
            }
        }
    }

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
        )

    ) {
        Box(
            modifier = modifier.background(Color.White)
        )
        {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {

                scale(scaleX = 15f, scaleY = 10f) {
                    drawCircle(
                        Color.Blue,
                        radius = 40.dp.toPx(),
                        center = this.center + Offset(0f, -160f)
                    )
                }
            }
            Column(
                modifier = Modifier
            ) {
                /* 顶部欢迎栏 */
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
                        }
                    }
                }
                Spacer(modifier = Modifier.size(20.dp))
                /* 病历 + 人 */
                Row {

                }
                /* 卡片操作栏 */
                Card(
                    modifier = Modifier.padding(
                        start = 20.dp,
                        end = 20.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 128.dp),
                        contentPadding = PaddingValues(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        items(operations.size) {
                            MyButton(
                                modifier = Modifier
                                    .height(80.dp)
                                    .padding(4.dp),
                                text = operations[it].text,
                                icon = operations[it].icon,
                                onClick = operations[it].onClick
                            ).show()
                        }
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun HomePagePreview() {
        HomePage(
            name = "小老弟"
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