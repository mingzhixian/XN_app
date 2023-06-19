package edu.swu.xn.app.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.swu.xn.app.R


@Composable
fun TopBar(
    backOnClick: () -> Unit = {},
    searchOnClick: () -> Unit = {},
    text: String = "",
    background: Color = colorResource(id = R.color.background)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, top = 10.dp, bottom = 20.dp)
            .background(background),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = backOnClick) {
            Icon(
                modifier = Modifier.size(26.dp),
                painter = painterResource(id = R.drawable.back),
                contentDescription = null,
                tint = colorResource(id = R.color.iconColor)
            )
        }
        Text(
            text = text
        )
        IconButton(onClick = searchOnClick) {
            Icon(
                modifier = Modifier.size(26.dp),
                painter = painterResource(id = R.drawable.search),
                contentDescription = null,
                tint = colorResource(id = R.color.iconColor)
            )
        }
    }
}

@Preview
@Composable
fun TopBarPreview() {
    TopBar()
}
