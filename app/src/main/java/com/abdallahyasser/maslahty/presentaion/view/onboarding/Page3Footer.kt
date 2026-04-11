package com.abdallahyasser.maslahty.presentaion.view.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abdallahyasser.maslahty.theme.GoldenDark
import com.abdallahyasser.maslahty.theme.GoldenYellow

// ============== Page 3 Footer ==============
@Composable
public fun Page3Footer(
    onStartClick: () -> Unit,
    onSecondaryClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(236.dp)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Step Indicator
            Page3StepIndicator(activeStep = 2)

            Spacer(modifier = Modifier.height(32.dp))

            // Primary CTA Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp),
                        ambientColor = GoldenYellow.copy(alpha = 0.25f),
                        spotColor = GoldenYellow.copy(alpha = 0.25f)
                    )
                    .background(
                        color = GoldenYellow,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onStartClick() },
                contentAlignment = Alignment.Center
            ) {
                // Inner glow line at top
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .align(Alignment.TopCenter)
                        .background(Color.White.copy(alpha = 0.3f))
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "ابدأ الآن",
                        color = GoldenDark,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 28.sp
                    )

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = GoldenDark,
                        modifier = Modifier.size(16.dp)
                    )

                }
            }

            Spacer(modifier = Modifier.height(32.dp))


        }
    }
}