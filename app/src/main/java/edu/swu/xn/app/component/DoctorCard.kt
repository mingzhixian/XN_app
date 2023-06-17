package edu.swu.xn.app.component


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import edu.swu.xn.app.entity.Doctor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorCard(
  modifier: Modifier = Modifier,
  doctor: Doctor,
  containerColor: Color = Color.White,
  onClick: () -> Unit = {},

  ) {
  Card(
    modifier = modifier
        .padding(10.dp)
        .fillMaxWidth()
        .border(
            width = 1.dp,
            color = Color.Gray,
            shape = MaterialTheme.shapes.medium
        ),
    colors = CardDefaults.cardColors(
      containerColor = containerColor,
    ),
    elevation = CardDefaults.cardElevation(
      defaultElevation = 0.dp
    ),
    onClick = onClick
  ) {
    Row(
      modifier = Modifier
          .padding(10.dp)
          .fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      AsyncImage(
        modifier = Modifier.size(150.dp)
            .clip(MaterialTheme.shapes.medium)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = MaterialTheme.shapes.medium
            ),
        model = doctor.avatar,
        contentDescription = null
      )
      Column {
        Text(
          modifier = Modifier.padding(
            start = 10.dp,
            bottom = 4.dp
          ),
          text = doctor.realName + " " + doctor.sex,
          fontSize = 18.sp,
          fontWeight = FontWeight.SemiBold
        )
        Text(
          modifier = Modifier.padding(
            start = 10.dp
          ),
          text = doctor.title,
          fontSize = 14.sp,
          color = Color.Gray
        )
        Text(
          modifier = Modifier.padding(
            start = 10.dp
          ),
          text = "Phone: ${doctor.phoneNumber}\nEmail: ${doctor.email}\n医生简介: ${doctor.introduce}",
          fontSize = 12.sp,
          color = Color.Gray
        )
      }
    }
  }
}

@Preview
@Composable
fun DoctorCardPreview() {
  DoctorCard(
    doctor = Doctor(
      20,
      "https://img0.baidu.com/it/u=3495541663,4174246784&fm=253&fmt=auto&app=120&f=JPEG?w=343&h=500",
      "10",
      "string.email",
      "78",
      "主治传染皮肤病",
      "110",
      "周矣",
      "男",
      "普通医生",
      "zhouyi01"
    )
  )
}
