package com.abdallahyasser.maslahty.presentaion.screens.vechicle.VehicleDetails
import VehicleDetailsViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.abdallahyasser.maslahty.presentaion.navigation.Route
import com.abdallahyasser.maslahty.theme.LocalAppColors
import com.example.maslahty.presentation.components.AppTextField
import com.example.maslahty.presentation.components.ErrorMessage
import com.example.maslahty.presentation.components.PrimaryButton

@Composable
fun VehicleDetailsScreen(navController: NavHostController) {
    val viewModel: VehicleDetailsViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()
    val appColors = LocalAppColors.current

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
                            Icons.Default.ArrowBack,
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
                            "الخطوة 1 من 4 — تحقق الملكية",
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

            // Step Indicator - Circular Steps
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    listOf("المركبة", "الصور", "السعر", "الإرسال").forEachIndexed { index, label ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .wrapContentWidth(align = Alignment.CenterHorizontally),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                // Circular Step Indicator
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (index == 0) Color(0xFFCD972E) else Color.White
                                        )
                                        .border(
                                            width = if (index == 0) 0.dp else 2.dp,
                                            color = if (index == 0) Color.Transparent else Color(0xFFE5E7EB),
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        (index + 1).toString(),
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (index == 0) Color.White else Color(0xFF9CA3AF)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    label,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (index == 0) Color(0xFFEAB308) else Color(0xFF9CA3AF),
                                    fontWeight = if (index == 0) FontWeight.SemiBold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }

                // Connecting Lines Background
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(horizontal = 16.dp)
                        .height(2.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(2.dp)
                                .background(
                                    color = Color(0xFFE5E7EB)
                                )
                        )
                    }
                }

                // Connecting Line Active (Partial)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(horizontal = 16.dp)
                        .height(2.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(0.7f)
                            .height(2.dp)
                            .background(color = Color(0xFFDFA123))
                    )
                    Box(
                        modifier = Modifier
                            .weight(2.3f)
                            .height(2.dp)
                    )
                }
            }

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
                    name = "أحمد محمد",
                    nationalId = "12345678901234",
                    isVerified = true
                )

                // Vehicle Section
                SectionHeader(title = "تعريف المركبة", icon = Icons.Default.Search)

                AppTextField(
                    value = state.licensePlate,
                    onValueChange = { viewModel.onLicensePlateChange(it.uppercase()) },
                    label = "رقم اللوحة",
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = Icons.Default.DirectionsCar
                )

                AppTextField(
                    value = state.chassisNumber,
                    onValueChange = { viewModel.onChassisNumberChange(it.uppercase()) },
                    label = "رقم الشاسيه",
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = Icons.Default.Info
                )

                AppTextField(
                    value = state.engineNumber,
                    onValueChange = { viewModel.onEngineNumberChange(it.uppercase()) },
                    label = "رقم الموتور",
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = Icons.Default.Build
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

                if (state.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp
                            )
                            Text(
                                "جاري التحقق...",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Bottom Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                PrimaryButton(
                    text = "التالي — رفع الصور",
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
                                // ✅ حفظ البيانات أولاً في TransferDraftStore
                                viewModel.saveVehicleDataAndNavigate(
                                    licensePlate = state.licensePlate,
                                    onNavigate = {
                                        navController.navigate(Route.ImageUpload(state.licensePlate))
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