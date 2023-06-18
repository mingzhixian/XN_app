package edu.swu.xn.app.component


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import edu.swu.xn.app.entity.CommonSense

/**
 *  常识卡片
 */
@Composable
fun CommonSenseCard(
    modifier: Modifier = Modifier,
    commonSense: CommonSense,
) {
    Card(
        modifier = modifier
          .padding(10.dp)
          .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                  .size(100.dp)
                  .clip(RoundedCornerShape(16.dp)),
                model = commonSense.url,
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column {
                Text(
                    text = commonSense.title,
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = commonSense.content,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview
@Composable
fun CommonSenseCardPreview() {
    val cs = CommonSense(
        url = "https://avatars.githubusercontent.com/u/78494317?s=40&v=4",
        title = "这是标题 Launching 'CommonSenseCardPreview' on HUAWEI ANA-AN00.",
        content = "这是内容 App restart successful without re-installing the following APK(s): edu.swu.xn.app.test"
    )
    CommonSenseCard(commonSense = cs)
}
