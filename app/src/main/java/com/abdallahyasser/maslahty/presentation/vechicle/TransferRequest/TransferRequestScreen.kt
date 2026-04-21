package com.abdallahyasser.maslahty.presentation.vechicle.TransferRequest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Summarize
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.abdallahyasser.maslahty.data.local.TransferDraft.TransferDraftStore
import com.abdallahyasser.maslahty.presentation.navigation.Route
import com.abdallahyasser.maslahty.theme.AppColors
import com.abdallahyasser.maslahty.theme.LocalAppColors
import com.example.maslahty.presentation.components.AppCard
import com.example.maslahty.presentation.components.AppTextField
import com.example.maslahty.presentation.components.ErrorMessage
import com.example.maslahty.presentation.components.GradientHeader
import com.example.maslahty.presentation.components.InfoRow
import com.example.maslahty.presentation.components.LoadingBox
import com.example.maslahty.presentation.components.PrimaryButton
import com.example.maslahty.presentation.components.SectionHeader
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.AttachMoney

// TODO: creat hilt ViewModel and inject it here instead of using viewModel() directly
@Composable
fun TransferRequestScreen(
    navController: NavHostController,
    vehicleId: String,

) {
    val viewModel: TransferRequestViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val appColors = LocalAppColors.current

    // حقن البيانات في البداية
    LaunchedEffect(vehicleId) {
        val draft = TransferDraftStore.drafts[vehicleId]
        viewModel.initData(vehicleId, draft)
    }

    // مراقبة النجاح للانتقال لشاشة أخرى
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            TransferDraftStore.drafts.remove(vehicleId)
            navController.navigate(Route.Home) {
                popUpTo(Route.Home) { inclusive = true }
            }
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)) {
            GradientHeader(
                title = "إرسال طلب النقل",
                subtitle = "الخطوة 4 من 4 — تأكيد وإرسال",
                onBack = { navController.popBackStack() },
                icon = Icons.Default.Send
            )

            TransferContent(
                uiState = uiState,
                appColors = appColors,
                onNationalIdChange = viewModel::onNationalIdChange,
                onNotesChange = viewModel::onNotesChange,
                onSubmit = viewModel::submitRequest
            )
        }
    }
}

@Composable
fun TransferContent(
    uiState: TransferRequestUiState,
    appColors: AppColors, // افترضت اسم الكلاس عندك
    onNationalIdChange: (String) -> Unit,
    onNotesChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // ملخص الطلب
        SectionHeader(title = "ملخص الطلب", icon = Icons.Default.Summarize)

        AppCard {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                InfoRow(label = "البائع", value = "Mostafa Al", icon = Icons.Default.Sell)
                Divider(color = appColors.cardBorder)
                InfoRow(label = "المركبة", value = uiState.draft?.vehicle?.licensePlate ?: "—", icon = Icons.Default.DirectionsCar)
                Divider(color = appColors.cardBorder)
                InfoRow(label = "سعر البيع", value = "EGP ${"%.0f".format(uiState.draft?.salePrice ?: 0.0)}", icon = Icons.Default.AttachMoney)
            }
        }

        // مدخلات البيانات
        SectionHeader(title = "بيانات المشتري", icon = Icons.Default.PersonSearch)

        AppTextField(
            value = uiState.buyerNationalId,
            onValueChange = onNationalIdChange,
            label = "الرقم القومي للمشتري",
            leadingIcon = Icons.Default.Badge,
            placeholder = "14 رقم",
            keyboardType = KeyboardType.Number,
            enabled = false
        )

        AppTextField(
            value = uiState.notes,
            onValueChange = onNotesChange,
            label = "ملاحظات إضافية (اختياري)",
            leadingIcon = Icons.Default.Notes,
            placeholder = "أي ملاحظات تريد إضافتها...",
            singleLine = false,
            maxLines = 4
        )

        uiState.error?.let { ErrorMessage(it) }

        if (uiState.isLoading) {
            LoadingBox(message = "جاري إرسال الطلب...")
        }

        PrimaryButton(
            text = "تأكيد وإرسال الطلب",
            icon = Icons.Default.Send,
            onClick = onSubmit,
            enabled = !uiState.isLoading
        )
    }
}