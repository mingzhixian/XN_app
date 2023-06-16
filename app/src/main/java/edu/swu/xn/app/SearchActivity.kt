package edu.swu.xn.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import edu.swu.xn.app.component.Search
import edu.swu.xn.app.entity.Doctor
import edu.swu.xn.app.ui.theme.AppTheme

class SearchActivity : AppCompatActivity() {
}

@Composable
fun SearchPage(
    modifier: Modifier = Modifier,
) {
    var searchText by rememberSaveable {
        mutableStateOf("")
    }

    var depts = remember {
        mutableStateListOf<String>(
            "心脏科室", "内科科室", "骨科科室"
        )
    }

    var doctors = remember {
        mutableStateListOf<Doctor>(
            Doctor(1, "", "", "", "", "", "", "", "", "", "")
        )
    }

    var colors = MaterialTheme.colorScheme
    AppTheme {
        colors = MaterialTheme.colorScheme
    }
    LazyColumn(
        modifier = modifier
            .background(colors.background)
            .fillMaxSize()
    ) {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(20.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = colors.secondaryContainer
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
                            appData.netHelper.get(
                                "${appData.main.getString(R.string.admin_url)}/api/service-user/doctor/searchDoctorOrDept"
                            ) { data ->
                                val deptList = data.getJSONObject("data").getJSONArray("deptNames")
                                for (i in 0 until deptList.length()) {
                                    depts.add(deptList.getString(i))
                                }
                                val doctorList =
                                    data.getJSONObject("data").getJSONArray("doctorList")
                                for (i in 0 until doctorList.length()) {
                                    doctors.add(
                                        Doctor(
                                            amount = doctorList.getJSONObject(i).getInt("amount"),
                                            avatar = doctorList.getJSONObject(i)
                                                .getString("avatar"),
                                            deptID = doctorList.getJSONObject(i)
                                                .getString("deptId"),
                                            email = doctorList.getJSONObject(i).getString("email"),
                                            id = doctorList.getJSONObject(i).getString("id"),
                                            introduce = doctorList.getJSONObject(i)
                                                .getString("introduce"),
                                            phoneNumber = doctorList.getJSONObject(i)
                                                .getString("phonenumber"),
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
                        },
                    )
                }
            }
        }
        if (depts.size > 0) {
            item {
                Text(
                    modifier = Modifier.padding(
                        start = 10.dp,
                        bottom = 4.dp
                    ),
                    text = "科室",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    modifier = Modifier.padding(
                        start = 10.dp
                    ),
                    text = "找到您可能想要的科室",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            items(depts.size) { index ->
                DeptCard(
                    deptName = depts[index],
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    containerColor = colors.background
                )
            }
        }

        if (doctors.size > 0) {
            item {
                Text(
                    modifier = Modifier.padding(
                        start = 10.dp,
                        bottom = 4.dp
                    ),
                    text = "医生",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
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
            items(doctors.size) {

            }
        }

    }
}


@Preview
@Composable
fun SearchPagePreview() {
    SearchPage()
}