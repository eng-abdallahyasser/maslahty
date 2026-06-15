package com.abdallahyasser.maslahty.presentation.transfer.contract

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.abdallahyasser.maslahty.presentation.navigation.Route
import com.abdallahyasser.maslahty.presentation.shared_composables.ErrorMessage
import com.abdallahyasser.maslahty.presentation.shared_composables.GradientHeader
import com.abdallahyasser.maslahty.presentation.transfer.contract.ImageContractViewModel
import com.abdallahyasser.maslahty.presentation.transfer.imageUpload.VerticalUploadSlot
import com.abdallahyasser.maslahty.theme.LocalAppColors
import com.example.maslahty.presentation.components.PrimaryButton
import com.example.maslahty.presentation.components.StepIndicator

@Composable
fun ImageContractScreen(
    navController: NavHostController,
    vehicleId: String,

    ){

    val viewModel: ImageContractViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val bgColor = MaterialTheme.colorScheme.background

    LaunchedEffect(vehicleId) {
        viewModel.loadDraftOrInitialize(vehicleId)
    }

    // تعريف الـ Launchers لفتح المعرض لكل صورة
    val contractLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val url = it.toString()
            viewModel.updateContractImage(url)
            // بعد ما نرفع الصورة ننتقل مباشرة للخطوة التالية (تفاصيل المركبة)
            viewModel.onNextClicked(vehicleId) {
                navController.navigate(Route.VehicleDetails(vehicleId = vehicleId))
            }
        }

    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(bgColor)
        ) {
            GradientHeader(
                title = "عقد مسجل",
                subtitle = "الخطوة 1 من5 — صوره العقد",
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
                    currentStep = 1,
                    totalSteps = 5,
                    stepLabels = listOf("العقد","المركبة", "الصور", "السعر", "الإرسال")
                )


                Spacer(Modifier.height(24.dp))

                // شبكة الصور
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    VerticalUploadSlot(
                        label = "صوره العقد المسجل",
                        value = state.imageContract,
                        onClick = { contractLauncher.launch("image/*") },
                        modifier = Modifier.weight(1f)
                    )

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
                        .navigationBarsPadding()
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
                        icon = Icons.AutoMirrored.Filled.ArrowForward,
                        enabled = state.uploadedCount == 1,
                        onClick = {
                            viewModel.onNextClicked(vehicleId) {
                                navController.navigate(Route.VehicleDetails(vehicleId = vehicleId))
                            }

                        }
                    )
                }
            }
            }
        }
}
