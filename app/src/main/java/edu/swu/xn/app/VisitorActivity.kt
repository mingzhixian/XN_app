package edu.swu.xn.app

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.swu.xn.app.entity.Visitor
import edu.swu.xn.app.ui.theme.AppTheme
import org.json.JSONObject

class VisitorActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      VisitorPage(context = this)
    }
  }

  @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
  @Composable
  fun VisitorPage(
    modifier: Modifier = Modifier,
    context: Context = this
  ) {
    var colors = MaterialTheme.colorScheme
    AppTheme {
      colors = MaterialTheme.colorScheme
    }

    val visitors = remember {
      mutableStateListOf(
        Visitor(
          name = "用户",
          id = 1,
          sex = "男",
          phoneNumber = "12345678",
          medicalHistory = "近期进行过大型手术",
          cardID = "4290052003",
          age = 15
        ),
        Visitor(
          name = "瓜娃",
          id = 2,
          sex = "女",
          phoneNumber = "x12345678",
          medicalHistory = "近期进行过小型手术",
          cardID = "42900520032222",
          age = 20
        )
      )
    }

    val obj = JSONObject()
    obj.put("userID", appData.userId)
    appData.netHelper.get(
      url = stringResource(R.string.admin_url) + "/api/service-user/patient/getPatientInfo",
      value = obj
    )
    { data ->
      val dataList = data.getJSONArray("data")
      for (i in 0 until dataList.length()) {
        visitors.add(
          Visitor(
            name = dataList.getJSONObject(i).getString("userName"),
            id = dataList.getJSONObject(i).getString("id").toInt(),
            sex = if (dataList.getJSONObject(i).getString("sex") == "0") {
              "男"
            } else {
              "女"
            },
            phoneNumber = dataList.getJSONObject(i).getString("phonenumber"),
            medicalHistory = dataList.getJSONObject(i).getString("medicalHistory"),
            cardID = dataList.getJSONObject(i).getString("cardId"),
            age = dataList.getJSONObject(i).getInt("age")
          )
        )
      }
    }

    val isAdd = remember { mutableStateOf(false) }
    val isUpdate = remember { mutableStateOf(false) }
    val isRead = remember { mutableStateOf(false) }
    val age = remember { mutableStateOf(0) }
    val isAgeError = remember { mutableStateOf(false) }
    fun validateAge(age: Int) {
      isAgeError.value = age in 0..200
    }

    val cardID = remember { mutableStateOf("") }
    val medicalHistory = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val sex = remember { mutableStateOf(false) } // true 男 false 女
    val name = remember { mutableStateOf("") }

    fun cleanStates() {
      age.value = 0
      isAgeError.value = false
      cardID.value = ""
      medicalHistory.value = ""
      phoneNumber.value = ""
      sex.value = false
      name.value = ""
    }

    fun setStates(index: Int) {
      age.value = visitors[index].age
      isAgeError.value = false
      cardID.value = visitors[index].cardID
      medicalHistory.value = visitors[index].medicalHistory
      phoneNumber.value = visitors[index].phoneNumber
      sex.value = visitors[index].sex == "男"
      name.value = visitors[index].name
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
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
      )
      {
        /* 标题 */
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
        /* 就诊人卡片 */
        items(visitors.size) { index ->
          Card(
            modifier = modifier
              .padding(10.dp)
              .fillMaxWidth()
              .combinedClickable(
                onLongClick = {
                  isUpdate.value = true
                  setStates(index)
                },
                onClick = {
                  isRead.value = true
                  setStates(index)
                }
              ),
            colors = CardDefaults.cardColors(
              containerColor = colors.background,
              contentColor = Color.Unspecified
            ),
            elevation = CardDefaults.cardElevation(
              defaultElevation = 4.dp
            ),
          ) {
            Row(
              modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
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
                    bottom = 2.dp
                  ),
                  text = "姓名:" + visitors[index].name,
                  fontSize = 18.sp,
                  fontWeight = FontWeight.SemiBold
                )
                Text(
                  modifier = Modifier.padding(
                    bottom = 2.dp
                  ),
                  text = "性别:" + visitors[index].sex,
                  fontSize = 14.sp,
                  color = Color.Gray
                )
                Text(
                  modifier = Modifier.padding(
                    bottom = 2.dp
                  ),
                  text = "年龄:" + visitors[index].age.toString() + "岁",
                  fontSize = 14.sp,
                  color = Color.Gray
                )
                Text(
                  modifier = Modifier.padding(
                  ),
                  text = "电话号码: ${visitors[index].phoneNumber}",
                  fontSize = 12.sp,
                  color = Color.Gray
                )
              }
              IconButton(
                modifier = Modifier.size(40.dp),
                onClick = {
                  val obj = JSONObject()
                  obj.put("id", visitors[index].id)
                  Log.i("hzd", obj.toString())
                  appData.netHelper.get(
                    appData.main.getString(R.string.admin_url) + "/api/service-user/patient/deletePatient",
                    obj
                  ) { data ->
                    if (data.getString("code") != "200") {
                      Toast.makeText(context, "删除失败！", Toast.LENGTH_LONG).show()
                    } else {
                      Toast.makeText(context, "删除成功！", Toast.LENGTH_LONG).show()
                      visitors.removeAt(index)
                    }
                  }
                }
              ) {
                Icon(
                  painter = painterResource(id = R.drawable.delete),
                  contentDescription = null
                )
              }
            }
          }
        }
        /* 添加就诊人按钮 */
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
            onClick = {
              isAdd.value = true
            },
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
      if (isAdd.value || isUpdate.value || isRead.value) {
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
            modifier = Modifier
              .padding(40.dp)
          ) {
            Text(
              text =
              if (isAdd.value)
                "新增就诊人"
              else
                if (isUpdate.value)
                  "更新就诊人信息"
                else
                  "就诊人信息",
              fontSize = 28.sp
            )
            /* 性别 */
            Row(
              modifier = Modifier.selectableGroup(),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text("性别:")
              RadioButton(selected = sex.value, onClick = {
                if (!isRead.value) {
                  sex.value = true
                }
              })
              Text(text = "男")
              RadioButton(selected = !sex.value, onClick = {
                if (!isRead.value) {
                  sex.value = false
                }
              })
              Text(text = "女")
            }
            /* 姓名 */
            Row {
              TextField(
                label = {
                  Text("姓名")
                },
                value = name.value,
                onValueChange = {
                  name.value = it
                },
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                readOnly = isRead.value
              )
            }
            /* 年龄 */
            Row {
              TextField(
                label = {
                  Text(
                    if (isAgeError.value) {
                      "年龄: 年龄区间为0~200"
                    } else {
                      "年龄"
                    }
                  )
                },
                value = age.value.toString(),
                onValueChange = {
                  if (it == "") {
                    age.value = 0
                  }
                  age.value = it.toInt()
                },
                keyboardOptions = KeyboardOptions(
                  keyboardType = KeyboardType.Number,
                ),
                keyboardActions = KeyboardActions {
                  validateAge(age.value)
                },
                singleLine = true,
                readOnly = isRead.value
              )
            }
            /* 手机号码 */
            Row {
              TextField(
                label = {
                  Text("手机号码")
                },
                value = phoneNumber.value,
                onValueChange = {
                  phoneNumber.value = it
                },
                singleLine = true,
                readOnly = isRead.value
              )
            }
            /* 身份证号码 */
            Row {
              TextField(
                label = {
                  Text("身份证号")
                },
                value = cardID.value,
                onValueChange = {
                  cardID.value = it
                },
                singleLine = true,
                readOnly = isRead.value
              )
            }
            /* 过往病史 */
            Row {
              TextField(
                label = {
                  Text("过往病史")
                },
                value = medicalHistory.value,
                onValueChange = {
                  medicalHistory.value = it
                },
                readOnly = isRead.value
              )
            }

            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
              horizontalArrangement = Arrangement.SpaceBetween
            )
            {
              Spacer(modifier = Modifier.size(1.dp))
              if (!isRead.value) {
                IconButton(
                  onClick = {
                    val obj = JSONObject()
                    obj.put("age", age.value)
                    obj.put("cardId", cardID.value)
                    obj.put("medicalHistory", medicalHistory.value)
                    obj.put("phonenumber", phoneNumber.value)
                    obj.put(
                      "sex", if (sex.value) {
                        "1"
                      } else {
                        "0"
                      }
                    )
                    obj.put("userId", appData.userId)
                    obj.put("userName", name.value)

                    appData.netHelper.get(
                      url = appData.main.getString(R.string.admin_url) +
                          if (isAdd.value) "/api/service-user/patient/addPatient"
                          else "/api/service-user/patient/modifyPatientInfo",
                      value = obj
                    ) { data ->
                      Log.i("hzd", data.toString())
                      if (data.getString("code") != "200") {
                        Toast.makeText(
                          context,
                          if (isAdd.value) "添加失败!" else "更新失败!",
                          Toast.LENGTH_LONG
                        ).show()
                      } else {
                        Toast.makeText(
                          context,
                          if (isAdd.value) "添加成功!" else "更新成功!",
                          Toast.LENGTH_LONG
                        ).show()
                      }
                      if (isAdd.value) {
                        isAdd.value = false
                      }
                      if (isUpdate.value) {
                        isUpdate.value = false
                      }
                      cleanStates()
                    }

                  }) {
                  Icon(
                    painter = painterResource(id = R.drawable.done),
                    contentDescription = null
                  )
                }
                Spacer(modifier = Modifier.size(1.dp))
              }
              IconButton(
                onClick = {
                  if (isAdd.value) {
                    isAdd.value = false
                  }
                  if (isUpdate.value) {
                    isUpdate.value = false
                  }
                  if (isRead.value) {
                    isRead.value = false
                  }
                  cleanStates()
                }) {
                Icon(
                  painter = painterResource(id = R.drawable.cancel),
                  contentDescription = null
                )
              }
              Spacer(modifier = Modifier.size(1.dp))
            }
          }
        }
      }
    }
  }

  @Preview
  @Composable
  fun VisitorPagePreview() {
    VisitorPage(context = this)
  }

}

