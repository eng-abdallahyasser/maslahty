package com.abdallahyasser.maslahty.presentation.transfer.vehicleDetails
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.abdallahyasser.maslahty.presentation.navigation.Route
import com.abdallahyasser.maslahty.theme.LocalAppColors
import com.example.maslahty.presentation.components.AppTextField
import com.example.maslahty.presentation.components.ErrorMessage
import com.example.maslahty.presentation.components.PrimaryButton
import com.example.maslahty.presentation.components.StepIndicator

@Composable
fun VehicleDetailsScreen(
    navController: NavHostController,
    vehicleId: String? = null,
    viewModel: VehicleViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val appColors = LocalAppColors.current

    LaunchedEffect(vehicleId) {
        if (vehicleId != null) {
            viewModel.loadVehicleData(vehicleId)
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Header with Gradient
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF111D37),
                                Color(0xFF1E2D4B)
                            )
                        )
                    )
                    .statusBarsPadding()
                    .padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "الرجوع",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "بيع مركبة",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "الخطوة 2 من 5 — تحقق الملكية",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                        )
                    }
                    Icon(
                        Icons.Default.DirectionsCar,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            // Step indicator
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                StepIndicator(
                    currentStep = 2,
                    totalSteps = 5,
                    stepLabels = listOf("العقد","المركبة", "الصور", "السعر", "الإرسال")
                )
            }

            if (state.isLoading) {
                Box(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = appColors.gold)
                }
            } else {
                // Content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    // Owner Section
                    SectionHeader(title = "بيانات المالك الحالي", icon = Icons.Default.Person)
                    OwnerCard(
                        name = state.ownerName.ifEmpty { "جاري التحميل..." },
                        nationalId = state.ownerNationalId.ifEmpty { "—" },
                        isVerified = true
                    )

                    // Vehicle Section
                    SectionHeader(title = "تعريف المركبة", icon = Icons.Default.Search)

                    AppTextField(
                        value = state.licensePlate,
                        onValueChange = { viewModel.onLicensePlateChange(it.uppercase()) },
                        label = "رقم اللوحة",
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = Icons.Default.DirectionsCar,
                        enabled = !state.isReadOnly
                    )

                    AppTextField(
                        value = state.chassisNumber,
                        onValueChange = { viewModel.onChassisNumberChange(it.uppercase()) },
                        label = "رقم الشاسيه",
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = Icons.Default.Info,
                        enabled = !state.isReadOnly
                    )

                    AppTextField(
                        value = state.engineNumber,
                        onValueChange = { viewModel.onEngineNumberChange(it.uppercase()) },
                        label = "رقم الموتور",
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = Icons.Default.Build,
                        enabled = !state.isReadOnly
                    )

                    AppTextField(
                        value = state.model,
                        onValueChange = { viewModel.onModelChange(it) },
                        label = "الموديل",
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = Icons.Default.DirectionsCar,
                        enabled = !state.isReadOnly
                    )

                    AppTextField(
                        value = state.manufacturingYear,
                        onValueChange = { viewModel.onManufacturingYearChange(it) },
                        label = "سنة التصنيع",
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = Icons.Default.CalendarMonth,
                        keyboardType = KeyboardType.Number,
                        enabled = !state.isReadOnly
                    )

                    AppTextField(
                        value = state.color,
                        onValueChange = { viewModel.onColorChange(it) },
                        label = "اللون",
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = Icons.Default.Palette,
                        enabled = !state.isReadOnly
                    )

                    // Buyer Section
                    SectionHeader(title = "بيانات المشتري", icon = Icons.Default.PersonSearch)

                    AppTextField(
                        value = state.newOwnerNationalId,
                        onValueChange = {
                            if (it.length <= 14 && it.all { c -> c.isDigit() }) {
                                viewModel.onNewOwnerNationalIdChange(it)
                            }
                        },
                        label = "الرقم القومي للمشتري",
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = Icons.Default.Person
                    )

                    state.error?.let { ErrorMessage(it) }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Bottom Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(20.dp)
            ) {
                PrimaryButton(
                    text = "التالي — رفع الصور",
                    icon = Icons.AutoMirrored.Filled.ArrowForward,
                    enabled = !state.isLoading && state.licensePlate.isNotEmpty(),
                    onClick = {
                        when {
                            state.licensePlate.isBlank() -> {
                                viewModel.setError( "أدخل رقم اللوحة")
                            }
                            state.newOwnerNationalId.length != 14 -> {
                                viewModel.setError ("الرقم القومي يجب أن يكون 14 رقم")
                            }
                            state.chassisNumber.isBlank() -> {
                                viewModel.setError("أدخل رقم الشاسيه")
                            }
                            state.engineNumber.isBlank() -> {
                                viewModel.setError("أدخل رقم الموتور")
                            }
                            else -> {
                                viewModel.saveVehicleDataAndNavigate(
                                    licensePlate = state.licensePlate,
                                    onNavigate = {
                                        navController.navigate(Route.ImageUpload(state.vehicleId.ifEmpty { state.licensePlate }))
                                    }
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String, icon: ImageVector) {
    val appColors = LocalAppColors.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(appColors.gold.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = appColors.gold,
                modifier = Modifier.size(18.dp)
            )
        }
        Text(
            title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun OwnerCard(name: String, nationalId: String, isVerified: Boolean) {
    val appColors = LocalAppColors.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(appColors.gold.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    tint = appColors.gold,
                    modifier = Modifier.size(26.dp)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    name,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    nationalId,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (isVerified) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(appColors.gold.copy(alpha = 0.2f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        "مُحقَّق",
                        style = MaterialTheme.typography.labelSmall,
                        color = appColors.gold,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}