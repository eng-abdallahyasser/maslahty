package com.abdallahyasser.maslahty.presentaion.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.abdallahyasser.maslahty.R
import com.abdallahyasser.maslahty.theme.DarkNavyLight
import com.abdallahyasser.maslahty.theme.HeaderGradientEnd

// ============== Car Transfer Visual ==============
@Composable
public fun CarTransferVisual() {
    Box(
        contentAlignment = Alignment.Center
    ) {
        // Central dark gradient square
        Box(
            modifier = Modifier
                .size(256.dp)
                .shadow(
                    elevation = 25.dp,
                    shape = RoundedCornerShape(48.dp),
                    ambientColor = Color.Black.copy(alpha = 0.25f),
                    spotColor = Color.Black.copy(alpha = 0.25f)
                )
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(DarkNavyLight, HeaderGradientEnd),
                        start = Offset(0f, 256f),
                        end = Offset(256f, 0f)
                    ),
                    shape = RoundedCornerShape(48.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            // Abstract texture overlay
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .graphicsLayer { alpha = 0.1f }
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.White,
                                Color.Transparent
                            ),
                            center = Offset(0f, 0f),
                            radius = 400f
                        ),
                        shape = RoundedCornerShape(48.dp)
                    )
            )

            // Content inside the dark square
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Top row: two glass cards with transfer dots
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Left glass card (car icon)
                    GlassCard(
                        width = 61,
                        height = 58,
                        iconSize = 27 to 24,
                        icon = ImageVector.vectorResource(id = R.drawable.person)
                    )

                    // Transfer dots
                    TransferDots()

                    // Right glass card (document icon)
                    GlassCard(
                        width = 58,
                        height = 58,
                        iconSize = 24 to 24,
                        icon = ImageVector.vectorResource(id = R.drawable.car)
                    )
                }

                // Car icon below
                Icon(
                    painter = painterResource(id = R.drawable.confirm),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(50.dp, 45.dp)
                )
            }
        }

        // Floating Card (bottom-right)
        FloatingStatusCard(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 8.dp, y = 16.dp)
        )
    }
}