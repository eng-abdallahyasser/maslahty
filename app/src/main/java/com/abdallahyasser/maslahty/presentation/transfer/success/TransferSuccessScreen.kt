package com.abdallahyasser.maslahty.presentation.transfer.success

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.abdallahyasser.maslahty.presentation.navigation.Route
import com.abdallahyasser.maslahty.theme.LocalAppColors
import com.example.maslahty.presentation.components.PrimaryButton

@Composable
fun TransferSuccessScreen(navController: NavHostController) {
    val appColors = LocalAppColors.current

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Success Illustration (Simplified version of the image)
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(appColors.navy, appColors.gradientEnd)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Outer circle for the checkmark
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(appColors.gold),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = appColors.navy,
                        modifier = Modifier.size(36.dp)
                    )
                }

                // Small Car Icon in the corner (as in image)
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .offset(x = (-10).dp, y = 10.dp)
                        .size(45.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.9f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.DirectionsCar,
                        contentDescription = null,
                        tint = appColors.navy,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Title
            Text(
                text = "تم إرسال الطلب\nبنجاح",
                style = MaterialTheme.typography.headlineMedium,
                color = appColors.textPrimary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 38.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Subtitle
            Text(
                text = "تم استلام طلبك وهو الآن قيد المراجعة. يمكنك متابعة حالة الطلب من قسم طلباتي.",
                style = MaterialTheme.typography.bodyMedium,
                color = appColors.textSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Status Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "حالة الطلب الحالية",
                            style = MaterialTheme.typography.labelLarge,
                            color = appColors.textPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = appColors.gold.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "قيد المعالجة",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = appColors.gold,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            Icons.Default.AccessTime,
                            contentDescription = null,
                            tint = appColors.textSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "الوقت المتوقع للمراجعة: 24 - 48 ساعة",
                            style = MaterialTheme.typography.bodySmall,
                            color = appColors.textSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Action Buttons
            PrimaryButton(
                text = "الذهاب إلى طلباتي",
                onClick = {
                    navController.navigate(Route.RequestsScreen) {
                        popUpTo(Route.Home)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = {
                    navController.navigate(Route.Home) {
                        popUpTo(Route.Home) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.3f))
            ) {
                Text(
                    text = "العودة للرئيسية",
                    style = MaterialTheme.typography.titleMedium,
                    color = appColors.textPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
