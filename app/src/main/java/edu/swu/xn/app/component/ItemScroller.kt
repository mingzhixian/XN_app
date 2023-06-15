package edu.swu.xn.app.component


import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun ItemScroller(
    modifier: Modifier = Modifier,

    ) {
    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
    ) {

    }
}

@Preview
@Composable
fun ItemScrollerPreview() {
    ItemScroller()
}
