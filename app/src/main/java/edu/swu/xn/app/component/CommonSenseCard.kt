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
import androidx.compose.material3.ExperimentalMaterial3Api
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonSenseCard(
    modifier: Modifier = Modifier,
    commonSense: CommonSense,
    containerColor: Color = Color.White,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp)),
                model = commonSense.imgUrl,
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
        imgUrl = "http://www.xnyy.cn/__local/7/80/AD/E112E1D2710394EDB1942A54571_900D5247_CBC5.png",
        url = "http://www.xnyy.cn/info/1031/19337.htm",
        title = "”凝心聚力，迎势启航”——2023年康复医学科责任组长竞聘活动及住培学员个人汇报总结大会",
        content = "为提高人员素质，更好地发挥责任组长在临床及管理中的骨干作用，同时将竞争机制全面引入临床管理中，更好地选拔、锻炼、培养骨干力量，优化队伍结构，打造合理的人才梯队。科室党支部支委会研究决定在全科开展2023年康复医学科科室护理组、治疗组及科研组的各组责任组长竞聘活动。本次活动采取“公平、公正、公开，鼓励优秀骨干积极参与”的原则，在科内择优选拔一批优秀的责任骨干，将协助科室强化临床管理、提高教学质量、推进科研人才培养，以此全面促进康复医学科的高质量发展。"
    )
    CommonSenseCard(commonSense = cs)
}
