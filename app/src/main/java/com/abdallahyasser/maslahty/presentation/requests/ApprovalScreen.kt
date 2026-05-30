package com.abdallahyasser.maslahty.presentation.requests

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.abdallahyasser.maslahty.theme.LocalAppColors
import com.example.maslahty.presentation.components.AppCard
import com.example.maslahty.presentation.components.AppTextField
import com.example.maslahty.presentation.components.ErrorMessage
import com.example.maslahty.presentation.components.GradientHeader
import com.example.maslahty.presentation.components.LoadingBox
import com.example.maslahty.presentation.components.PrimaryButton
import com.example.maslahty.presentation.components.SecondaryButton
import com.example.maslahty.presentation.components.SectionHeader

@Composable
fun ApprovalScreen(
    navController: NavHostController,
    requestId: String,
    viewModel: RequestsViewModel = hiltViewModel()
) {
    val appColors = LocalAppColors.current
    var buyerId by remember { mutableStateOf("user2") }
    var error by remember { mutableStateOf<String?>(null) }
    val approveState by viewModel.approveRequestState.collectAsState()

    LaunchedEffect(approveState) {
        when (approveState) {
            is TransferState.RequestApproved -> navController.popBackStack()
            is TransferState.Error -> error = (approveState as TransferState.Error).message
            else -> Unit
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {

            GradientHeader(
                title = "اعتماد الطلب",
                subtitle = "موافقة المشتري على نقل الملكية",
                onBack = { navController.popBackStack() },
                icon = Icons.Default.Verified
            )

            Column(
                modifier = Modifier.fillMaxWidth().weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Confirmation banner
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Brush.linearGradient(listOf(Color(0xFF1B5E20), Color(0xFF2E7D32))))
                        .padding(20.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                        Box(
                            modifier = Modifier.size(52.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Verified, null, tint = Color.White, modifier = Modifier.size(30.dp))
                        }
                        Column {
                            Text("تأكيد استلام إشعار النقل", style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.Bold)
                            Text("رقم الطلب: #${requestId.takeLast(8)}", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.7f))
                        }
                    }
                }

                SectionHeader(title = "تأكيد الهوية", icon = Icons.Default.Security)

                AppTextField(
                    value = buyerId,
                    onValueChange = { buyerId = it },
                    label = "معرّف المشتري",
                    leadingIcon = Icons.Default.Person
                )

                // Security note
                AppCard {
                    Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Icon(Icons.Default.Lock, null, tint = appColors.gold, modifier = Modifier.size(20.dp))
                        Column {
                            Text("تنبيه أمني", style = MaterialTheme.typography.labelMedium, color = appColors.textPrimary, fontWeight = FontWeight.Bold)
                            Text(
                                "بالموافقة يُعدّ نقل الملكية رسمياً ملزماً قانونياً ولا يمكن التراجع عنه.",
                                style = MaterialTheme.typography.bodySmall,
                                color = appColors.textSecondary
                            )
                        }
                    }
                }

                error?.let { ErrorMessage(it) }
                if (approveState is TransferState.Loading) LoadingBox(message = "جاري اعتماد الطلب...")

                PrimaryButton(
                    text = "موافق — إتمام نقل الملكية",
                    icon = Icons.Default.CheckCircle,
                    onClick = {
                        if (buyerId.isBlank()) error = "أدخل معرّف المشتري"
                        else viewModel.approveRequest(requestId = requestId, buyerId = buyerId)
                    }
                )

                SecondaryButton(
                    text = "رفض الطلب",
                    icon = Icons.Default.Cancel,
                    onClick = { navController.popBackStack() }
                )

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}