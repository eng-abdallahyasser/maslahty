package com.abdallahyasser.maslahty.presentation.vechicle.ImageUpload

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.abdallahyasser.maslahty.theme.LocalAppColors
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import com.abdallahyasser.maslahty.presentation.navigation.Route
import com.abdallahyasser.maslahty.presentation.shared_composables.ErrorMessage
import com.abdallahyasser.maslahty.presentation.shared_composables.GradientHeader
import com.example.maslahty.presentation.components.PrimaryButton
import com.example.maslahty.presentation.components.StepIndicator

@Composable
fun ImageUploadScreen(
    navController: NavHostController,
    vehicleId: String,
) {
    val viewModel: ImageUploadViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()
    val appColors = LocalAppColors.current
    val bgColor = Color(0xFFF8FAFC)

    // تعريف الـ Launchers لفتح المعرض لكل صورة
    val licenseLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { viewModel.updateLicenseImage(it.toString()) }
    }
    val vehicleLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { viewModel.updateVehicleImage(it.toString()) }
    }
    val chassisLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { viewModel.updateChassisImage(it.toString()) }
    }
    val engineLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { viewModel.updateEngineImage(it.toString()) }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(bgColor)
        ) {
            GradientHeader(
                title = "بيع مركبة",
                subtitle = "الخطوة 2 من 4 — الصور",
                onBack = { navController.popBackStack() },
                icon = Icons.Default.DirectionsCar
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(Modifier.height(20.dp))
                StepIndicator(
                    currentStep = 2,
                    totalSteps = 4,
                    stepLabels = listOf("المركبة", "الصور", "السعر", "الإرسال")
                )

                Spacer(Modifier.height(24.dp))

                // شبكة الصور
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    VerticalUploadSlot(
                        label = "أمام المركبة",
                        value = state.licenseImageUrl,
                        onClick = { licenseLauncher.launch("image/*") },
                        modifier = Modifier.weight(1f)
                    )
                    VerticalUploadSlot(
                        label = "خلف المركبة",
                        value = state.vehicleImageUrl,
                        onClick = { vehicleLauncher.launch("image/*") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    VerticalUploadSlot(
                        label = "داخلية المركبة",
                        value = state.chassisImageUrl,
                        onClick = { chassisLauncher.launch("image/*") },
                        modifier = Modifier.weight(1f)
                    )
                    VerticalUploadSlot(
                        label = "لوحة المركبة",
                        value = state.engineImageUrl,
                        onClick = { engineLauncher.launch("image/*") },
                        modifier = Modifier.weight(1f)
                    )
                }

                state.error?.let {
                    Spacer(Modifier.height(16.dp))
                    ErrorMessage(it)
                }

                Spacer(Modifier.height(24.dp))
            }

            // الفوتر
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 32.dp),
                horizontalAlignment = Alignment.End
            ) {
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E293B)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.width(100.dp)
                ) {
                    Text("السابق", color = Color.White, fontWeight = FontWeight.Bold)
                }

                Spacer(Modifier.height(16.dp))

                PrimaryButton(
                    text = "التالي",
                    icon = Icons.Default.ArrowBack,
                    enabled = state.uploadedCount == 4,
                    onClick = {
                        viewModel.onNextClicked(vehicleId) {
                            navController.navigate(Route.Pricing(vehicleId))
                        }

                    }
                )
            }
        }
    }
}

@Composable
fun VerticalUploadSlot(
    label: String,
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hasValue = value.isNotBlank()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B)
            )
            Spacer(Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp) // زودت الارتفاع شوية عشان الصورة تبان أوضح
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF1F5F9)),
                contentAlignment = Alignment.Center
            ) {
                if (hasValue) {
                    // عرض الصورة المختارة
                    AsyncImage(
                        model = value,
                        contentDescription = label,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop // عشان الصورة تملى المربع بشكل احترافي
                    )
                } else {
                    // الأيقونة الافتراضية لو مفيش صورة
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = null,
                        tint = Color(0xFF94A3B8),
                        modifier = Modifier.size(32.dp)
                    )
                }

                // علامة الزائد (تظهر دايماً كـ Overlay أو فقط لو مفيش صورة حسب رغبتك)
                // هنا هخليها تظهر في الركن كـ مؤشر للإضافة/التعديل
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(1.dp, Color(0xFFE2E8F0), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (hasValue) Icons.Default.Edit else Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = if (hasValue) Color(0xFFFDC003) else Color(0xFF94A3B8)
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            Text(
                text = if (hasValue) "تعديل الصورة" else "إضافة صورة",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = if (hasValue) Color(0xFFFDC003) else Color(0xFF1E293B)
            )
        }
    }
}