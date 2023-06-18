package edu.swu.xn.app.component


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.swu.xn.app.R

/**
 *  部门、科室卡片
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeptCard(
    modifier: Modifier = Modifier,
    deptName: String = "",
    onClick: () -> Unit = {},
    containerColor: Color = Color.White,
    painter: Painter = painterResource(id = R.drawable.hospital)
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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp)),
                painter = painter,
                contentDescription = null
            )
            Text(
                text = deptName,
                color = Color(0xFF112950),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.right_arrow),
                contentDescription = null,
            )
        }
    }
}

@Preview
@Composable
fun DeptCardPreview() {
    DeptCard(deptName = "心脏科室")
}
