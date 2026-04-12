package com.abdallahyasser.maslahty.presentaion.view.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abdallahyasser.maslahty.R
import com.abdallahyasser.maslahty.theme.GoldenDark
import com.abdallahyasser.maslahty.theme.GoldenYellow

// ============== App Icon Brand Component (Page 1) ==============
@Composable
public fun AppIconBrand() {
    Box(
        modifier = Modifier.size(128.dp),
        contentAlignment = Alignment.Center
    ) {
        // Rotated golden background square
        Box(
            modifier = Modifier
                .size(128.dp)
                .offset(x = (-12).dp, y = (-12).dp)
                .graphicsLayer { rotationZ = 12f }
                .background(
                    color = GoldenYellow.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(24.dp)
                )
        )

        // Glass-morphism card
        Box(
            modifier = Modifier
                .size(128.dp)
                .shadow(
                    elevation = 25.dp,
                    shape = RoundedCornerShape(24.dp),
                    ambientColor = Color.Black.copy(alpha = 0.25f),
                    spotColor = Color.Black.copy(alpha = 0.25f)
                )
                .background(
                    color = Color.White.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(24.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            // Main icon
            val image = ImageVector.vectorResource(id = R.drawable.icoon)
            Icon(
                imageVector = image,
                contentDescription = "App Icon",
                tint = GoldenYellow,
                modifier = Modifier.size(50.dp)
            )
        }

        // Small badge at bottom-right
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 7.dp, y = 7.dp)
                .size(36.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = CircleShape,
                    ambientColor = Color.Black.copy(alpha = 0.1f),
                    spotColor = Color.Black.copy(alpha = 0.1f)
                )
                .background(
                    color = GoldenYellow,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        color = GoldenYellow,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✓",
                    color = GoldenDark,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}