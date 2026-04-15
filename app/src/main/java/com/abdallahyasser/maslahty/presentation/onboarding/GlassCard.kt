package com.abdallahyasser.maslahty.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.abdallahyasser.maslahty.theme.GoldenYellow

// ============== Glass Card ==============
@Composable
public fun GlassCard(
    width: Int,
    height: Int,
    iconSize: Pair<Int, Int>,
    icon: ImageVector

) {
    Box(
        modifier = Modifier
            .size(width.dp, height.dp)
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.1f),
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                color = Color.White.copy(alpha = 0.1f),
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = GoldenYellow,
            modifier = Modifier.size(iconSize.first.dp, iconSize.second.dp)
        )
    }
}