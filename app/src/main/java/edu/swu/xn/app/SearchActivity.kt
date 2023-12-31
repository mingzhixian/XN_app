package edu.swu.xn.app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import edu.swu.xn.app.component.DeptCard
import edu.swu.xn.app.component.DoctorCard
import edu.swu.xn.app.component.LoadingProgress
import edu.swu.xn.app.component.Search
import edu.swu.xn.app.component.TopRoundBackground
import edu.swu.xn.app.entity.Doctor
import edu.swu.xn.app.ui.theme.AppTheme
import org.json.JSONObject

class SearchActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    appData.publicTools.setStatusAndNavBar(this)
    setContent {
      AppTheme {
        SearchPage(colors = MaterialTheme.colorScheme)
      }
    }
  }

  @Composable
  fun SearchPage(
    modifier: Modifier = Modifier,
    colors: ColorScheme = MaterialTheme.colorScheme
  ) {
    var searchText by rememberSaveable { mutableStateOf("") }

    val depts = remember { mutableStateListOf<String>() }

    val doctors = remember { mutableStateListOf<Doctor>() }

    val progress = remember { mutableStateOf(false) }


    /* 顶部背景椭圆 */
    TopRoundBackground(
      backgroundColor = colors.background,
      containerColor = colors.primary,
    )
    Box {
      if (progress.value)
        LoadingProgress()

      LazyColumn(
        modifier = modifier
          .fillMaxSize()
      ) {

        item {
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .wrapContentHeight()
              .padding(20.dp),
            elevation = CardDefaults.cardElevation(
              defaultElevation = 0.dp
            ),
            colors = CardDefaults.cardColors(
              containerColor = Color.Transparent
            )
          )
          {
            Column(
              modifier = Modifier,
              verticalArrangement = Arrangement.Center,
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Image(
                painter = painterResource(id = R.drawable.hospital),
                modifier = Modifier.size(100.dp),
                contentDescription = null
              )
              Search(
                text = searchText,
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(20.dp),
                placeholderText = "查找医生或科室...",
                onTextChanged = {
                  searchText = it
                },
                onSearchDone = {
                  progress.value = true
                  /* 请求查询医生与部门 */
                  val obj = JSONObject()
                  obj.put("data", searchText)
                  appData.netHelper.get(
                    "${appData.main.getString(R.string.admin_url)}/api/service-user/doctor/searchDoctorOrDept",
                    value = obj
                  ) { data ->
                    depts.clear()
                    doctors.clear()
                    progress.value = false
                    if (data == null) return@get
                    if (data.length() == 0) {
                      Toast.makeText(this@SearchActivity, "搜索结果为空", Toast.LENGTH_LONG).show()
                      progress.value = false
                      return@get
                    }

                    val deptList = data.getJSONObject("data").getJSONArray("deptNames")
                    if (deptList.length() > 0) {
                      for (i in 0 until deptList.length()) {
                        depts.add(deptList.getString(i))
                      }
                    }

                    val doctorList =
                      data.getJSONObject("data").getJSONArray("doctorList")
                    if (doctorList.length() > 0) {
                      for (i in 0 until doctorList.length()) {
                        doctors.add(
                          Doctor(
                            amount = doctorList.getJSONObject(i).getInt("amount"),
                            avatar =
                            try {
                              doctorList.getJSONObject(i)
                                .getString("avatar")
                            } catch (_: Exception) {
                              "https://diy.jiuwa.net/up/6300b68ff3ae1.png"
                            },
                            deptID = doctorList.getJSONObject(i)
                              .getString("deptId"),
                            email =
                            try {
                              doctorList.getJSONObject(i).getString("email")
                            } catch (_: Exception) {
                              ""
                            },
                            id = doctorList.getJSONObject(i).getString("id"),
                            introduce =
                            try {
                              doctorList.getJSONObject(i)
                                .getString("introduce")
                            } catch (_: Exception) {
                              ""
                            },
                            phoneNumber =
                            try {
                              doctorList.getJSONObject(i)
                                .getString("phonenumber")
                            } catch (_: Exception) {
                              ""
                            },
                            realName = doctorList.getJSONObject(i)
                              .getString("realName"),
                            sex = if (doctorList.getJSONObject(i)
                                .getString("sex") == "0"
                            ) {
                              "男"
                            } else {
                              "女"
                            },
                            title = doctorList.getJSONObject(i).getString("title"),
                            userName = doctorList.getJSONObject(i)
                              .getString("userName"),
                          )
                        )
                      }
                    }
                  }
                },
              )
            }
          }
        }

        if (depts.size > 0) {
          item {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .background(colors.background)
            ) {
              Text(
                modifier = Modifier.padding(
                  start = 10.dp,
                  bottom = 4.dp
                ),
                text = "门诊",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                modifier = Modifier.padding(
                  start = 10.dp
                ),
                text = "找到您可能想要的诊室",
                fontSize = 14.sp,
                color = Color.Gray
              )
            }
          }
          items(depts.size) { index ->
            DeptCard(
              deptName = depts[index],
              modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(colors.background),
              containerColor = Color(appData.main.getColor(R.color.cardBackground)),
              onClick = {
                val intent = Intent(this@SearchActivity, AppointmentActivity::class.java)
                intent.putExtra("deptName", depts[index])
                startActivity(intent)
              }
            )
          }
        }

        if (doctors.size > 0) {
          item {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .background(colors.background)
            ) {
              Text(
                modifier = Modifier.padding(
                  start = 10.dp,
                  bottom = 4.dp
                ),
                text = "医生",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                modifier = Modifier.padding(
                  start = 10.dp
                ),
                text = "找到您可能想要的医生",
                fontSize = 14.sp,
                color = Color.Gray
              )
            }
          }
          items(doctors.size) { index ->
            DoctorCard(
              modifier = Modifier
                .fillMaxWidth()
                .background(colors.background),
              doctor = doctors[index],
              containerColor = Color(appData.main.getColor(R.color.cardBackground)),
              onClick = {
                val intent = Intent(this@SearchActivity, DoctorActivity::class.java)
                intent.putExtra("realName", doctors[index].realName)
                intent.putExtra("sex", if (doctors[index].sex == "男") "0" else "1")
                intent.putExtra("introduce", doctors[index].introduce)
                intent.putExtra("title", doctors[index].title)
                intent.putExtra("forWhat", "forDoctorDetail")
                intent.putExtra("avatar", doctors[index].avatar)
                startActivity(intent)
              }
            )
          }
        }

      }
    }
  }


  @Preview
  @Composable
  fun SearchPagePreview() {
    AppTheme {
      SearchPage(colors = MaterialTheme.colorScheme)
    }
  }
}