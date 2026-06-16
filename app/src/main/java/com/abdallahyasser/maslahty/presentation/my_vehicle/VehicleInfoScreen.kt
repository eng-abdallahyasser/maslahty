package com.abdallahyasser.maslahty.presentation.my_vehicle

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.abdallahyasser.maslahty.domain.vehicle.entity.Vehicle
import com.abdallahyasser.maslahty.domain.vehicle.entity.VehicleCondition
import com.abdallahyasser.maslahty.presentation.navigation.Route
import com.abdallahyasser.maslahty.theme.LocalAppColors
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun VehicleInfoScreen(
    navController: NavHostController,
    vehicleId: String,
    viewModel: MyVehicleViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val appColors = LocalAppColors.current
    val vehicle = state.vehicles.find { it.id == vehicleId }

    // Staggered animation state
    var showContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(150)
        showContent = true
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // ── GRADIENT HEADER ──────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(appColors.navy, appColors.gradientEnd)
                        )
                    )
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        IconButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.12f))
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "رجوع",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(appColors.gold.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.DirectionsCar,
                                contentDescription = null,
                                tint = appColors.gold,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "تفاصيل المركبة",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "معلومات كاملة عن مركبتك",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.6f)
                            )
                        }
                    }

                    if (vehicle != null) {
                        Spacer(modifier = Modifier.height(20.dp))

                        // Vehicle name hero area
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.White.copy(alpha = 0.08f))
                                .padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(appColors.gold.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.DirectionsCar,
                                        contentDescription = null,
                                        tint = appColors.gold,
                                        modifier = Modifier.size(36.dp)
                                    )
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = vehicle.model.ifBlank { "غير محدد" },
                                        style = MaterialTheme.typography.titleLarge,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(appColors.gold.copy(alpha = 0.2f))
                                            .padding(horizontal = 10.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = vehicle.licensePlate.ifBlank { "بدون لوحة" },
                                            style = MaterialTheme.typography.labelMedium,
                                            color = appColors.gold,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // ── BODY CONTENT ─────────────────────────────────
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = appColors.gold)
                }
            } else if (vehicle == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.ErrorOutline,
                            contentDescription = null,
                            tint = appColors.textSecondary,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            "لم يتم العثور على المركبة",
                            style = MaterialTheme.typography.titleMedium,
                            color = appColors.textSecondary
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // ── Quick Stats Row ──
                    AnimatedVisibility(
                        visible = showContent,
                        enter = fadeIn() + slideInVertically(
                            initialOffsetY = { it / 4 },
                            animationSpec = spring(stiffness = Spring.StiffnessLow)
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            QuickInfoChip(
                                icon = Icons.Default.CalendarMonth,
                                label = "سنة الصنع",
                                value = "${vehicle.manufacturingYear}",
                                modifier = Modifier.weight(1f)
                            )
                            QuickInfoChip(
                                icon = Icons.Default.Speed,
                                label = "الكيلومترات",
                                value = "%,d".format(vehicle.kilometers),
                                modifier = Modifier.weight(1f)
                            )
                            QuickInfoChip(
                                icon = Icons.Default.Star,
                                label = "الحالة",
                                value = conditionToArabic(vehicle.condition),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // ── Basic Info Section ──
                    AnimatedVisibility(
                        visible = showContent,
                        enter = fadeIn() + slideInVertically(
                            initialOffsetY = { it / 3 },
                            animationSpec = spring(
                                stiffness = Spring.StiffnessLow,
                                dampingRatio = Spring.DampingRatioMediumBouncy
                            )
                        )
                    ) {
                        InfoSection(
                            title = "المعلومات الأساسية",
                            icon = Icons.Default.Info,
                            items = listOf(
                                InfoItem("الموديل", vehicle.model.ifBlank { "غير محدد" }, Icons.Default.DirectionsCar),
                                InfoItem("اللون", vehicle.color.ifBlank { "غير محدد" }, Icons.Default.Palette),
                                InfoItem("رقم اللوحة", vehicle.licensePlate.ifBlank { "غير متوفر" }, Icons.Default.Badge),
                                InfoItem("سنة الصنع", "${vehicle.manufacturingYear}", Icons.Default.CalendarMonth)
                            )
                        )
                    }

                    // ── Technical Info Section ──
                    AnimatedVisibility(
                        visible = showContent,
                        enter = fadeIn() + slideInVertically(
                            initialOffsetY = { it / 3 },
                            animationSpec = spring(
                                stiffness = Spring.StiffnessLow,
                                dampingRatio = Spring.DampingRatioMediumBouncy
                            )
                        )
                    ) {
                        InfoSection(
                            title = "المعلومات الفنية",
                            icon = Icons.Default.Build,
                            items = listOf(
                                InfoItem("رقم الشاسيه", vehicle.chassisNumber.ifBlank { "غير متوفر" }, Icons.Default.Numbers),
                                InfoItem("رقم المحرك", vehicle.engineNumber.ifBlank { "غير متوفر" }, Icons.Default.Settings),
                                InfoItem("عداد الكيلومترات", "%,d كم".format(vehicle.kilometers), Icons.Default.Speed),
                                InfoItem("حالة المركبة", conditionToArabic(vehicle.condition), Icons.Default.Star)
                            )
                        )
                    }

                    // ── Dates Section ──
                    AnimatedVisibility(
                        visible = showContent,
                        enter = fadeIn() + slideInVertically(
                            initialOffsetY = { it / 3 },
                            animationSpec = spring(
                                stiffness = Spring.StiffnessLow,
                                dampingRatio = Spring.DampingRatioMediumBouncy
                            )
                        )
                    ) {
                        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("ar"))
                        InfoSection(
                            title = "التواريخ",
                            icon = Icons.Default.Event,
                            items = listOf(
                                InfoItem("تاريخ التسجيل", dateFormat.format(vehicle.createdAt), Icons.Default.DateRange),
                                InfoItem("آخر تحديث", dateFormat.format(vehicle.updatedAt), Icons.Default.Update)
                            )
                        )
                    }

                    // ── Sell Vehicle Button ──
                    AnimatedVisibility(
                        visible = showContent,
                        enter = fadeIn() + slideInVertically(
                            initialOffsetY = { it / 2 },
                            animationSpec = spring(stiffness = Spring.StiffnessLow)
                        )
                    ) {
                        Button(
                            onClick = {
                                navController.navigate(Route.ImageContractUpload(vehicleId = vehicle.id))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = appColors.gold,
                                contentColor = Color(0xFF0D1B3E)
                            ),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                        ) {
                            Icon(Icons.Default.Sell, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "بيع هذه المركبة",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }

                    Spacer(Modifier.navigationBarsPadding().height(16.dp))
                }
            }
        }
    }
}

// ── Quick Info Chip ──────────────────────────────────────────
@Composable
private fun QuickInfoChip(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = appColors.cardBackground),
        border = androidx.compose.foundation.BorderStroke(1.dp, appColors.cardBorder)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(appColors.gold.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = appColors.gold,
                    modifier = Modifier.size(18.dp)
                )
            }
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall,
                color = appColors.textPrimary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = appColors.textSecondary,
                fontSize = 10.sp
            )
        }
    }
}

// ── Info Section Card ────────────────────────────────────────
private data class InfoItem(
    val label: String,
    val value: String,
    val icon: ImageVector
)

@Composable
private fun InfoSection(
    title: String,
    icon: ImageVector,
    items: List<InfoItem>
) {
    val appColors = LocalAppColors.current
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = appColors.cardBackground),
        border = androidx.compose.foundation.BorderStroke(1.dp, appColors.cardBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Section header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(appColors.navy.copy(alpha = 0.08f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = appColors.navy,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = appColors.textPrimary,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(14.dp))

            items.forEachIndexed { index, item ->
                InfoRow(item)
                if (index < items.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 10.dp),
                        thickness = 0.5.dp,
                        color = appColors.cardBorder
                    )
                }
            }
        }
    }
}

@Composable
private fun InfoRow(item: InfoItem) {
    val appColors = LocalAppColors.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(appColors.gold.copy(alpha = 0.08f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = appColors.gold,
                modifier = Modifier.size(16.dp)
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.label,
                style = MaterialTheme.typography.labelSmall,
                color = appColors.textSecondary,
                fontSize = 11.sp
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = item.value,
                style = MaterialTheme.typography.bodyMedium,
                color = appColors.textPrimary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

// ── Condition Localization ───────────────────────────────────
private fun conditionToArabic(condition: VehicleCondition): String {
    return when (condition) {
        VehicleCondition.EXCELLENT -> "ممتازة"
        VehicleCondition.VERY_GOOD -> "جيدة جداً"
        VehicleCondition.GOOD -> "جيدة"
        VehicleCondition.FAIR -> "مقبولة"
        VehicleCondition.POOR -> "ضعيفة"
    }
}
