package edu.swu.xn.app.entity

import androidx.compose.ui.graphics.painter.Painter

data class Operation(
    val text: String,
    val icon: Painter,
    val onClick: () -> Unit
)
