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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.abdallahyasser.maslahty.data.local.TransferDraft.TransferDraftStore
import com.abdallahyasser.maslahty.domain.transfer.entity.TransferStatus
import com.abdallahyasser.maslahty.presentation.navigation.Route
import com.abdallahyasser.maslahty.theme.LocalAppColors
import com.example.maslahty.presentation.components.AppCard
import com.example.maslahty.presentation.components.ErrorMessage
import com.example.maslahty.presentation.components.GradientHeader
import com.example.maslahty.presentation.components.InfoRow
import com.example.maslahty.presentation.components.PriceWarningBox
import com.example.maslahty.presentation.components.PrimaryButton
import com.example.maslahty.presentation.components.SecondaryButton
import com.example.maslahty.presentation.components.SectionHeader
import com.example.maslahty.presentation.components.StatusBadge

@Composable
fun RequestDetailsScreen(navController: NavHostController, requestId: String) {
    val appColors = LocalAppColors.current
    val request = TransferDraftStore.lastLoadedRequests.firstOrNull { it.id == requestId }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)) {

            GradientHeader(
                title = "تفاصيل الطلب",
                subtitle = "عرض كامل بيانات طلب النقل",
                onBack = { navController.popBackStack() },
                icon = Icons.Default.Info
            )

            if (request == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    ErrorMessage("لا يمكن تحميل بيانات الطلب. ارجع وحمّل الطلبات مرة أخرى.")
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    // Status header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "رقم الطلب",
                                style = MaterialTheme.typography.labelSmall,
                                color = appColors.textTertiary
                            )
                            Text(
                                "#${requestId.takeLast(8)}",
                                style = MaterialTheme.typography.titleMedium,
                                color = appColors.textPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        StatusBadge(status = request.status.name)
                    }

                    // Parties card
                    SectionHeader(title = "أطراف العملية", icon = Icons.Default.Group)
                    AppCard {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            PartyRow(
                                role = "البائع",
                                name = request.sellerName,
                                id = request.sellerId,
                                icon = Icons.Default.Sell,
                                color = appColors.gold
                            )
                            Divider(color = appColors.cardBorder)
                            PartyRow(
                                role = "المشتري",
                                name = request.buyerName.ifBlank { "—" },
                                id = request.buyerId
                                    ?: "mockId",
                                icon = Icons.Default.ShoppingCart,
                                color = appColors.navy
                            )
                        }
                    }

                    // Vehicle & price
                    SectionHeader(title = "بيانات الصفقة", icon = Icons.Default.DirectionsCar)
                    AppCard {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            InfoRow(
                                label = "المركبة",
                                value = request.vehicleId,
                                icon = Icons.Default.DirectionsCar
                            )
                            Divider(color = appColors.cardBorder)
                            InfoRow(
                                label = "سعر البيع",
                                value = "EGP ${"%.0f".format(request.price)}",
                                icon = Icons.Default.AttachMoney
                            )
                            Divider(color = appColors.cardBorder)
                            InfoRow(
                                label = "الملاحظات",
                                value = request.notes.ifBlank { "لا توجد ملاحظات" },
                                icon = Icons.Default.Notes
                            )
                        }
                    }

                    request.priceWarning?.let {
                        PriceWarningBox(message = it.message, percentage = it.percentage)
                    }

                    // Action buttons
                    if (request.status == TransferStatus.PENDING) {
                        PrimaryButton(
                            text = "الموافقة على الطلب",
                            icon = Icons.Default.CheckCircle,
                            onClick = { navController.navigate(Route.ApprovalScreen(requestId = requestId)) }
                        )
                        SecondaryButton(
                            text = "رفض الطلب",
                            icon = Icons.Default.Cancel,
                            onClick = { navController.popBackStack() }
                        )
                    } else {
                        SecondaryButton(
                            text = "رجوع",
                            icon = Icons.Default.ArrowForward,
                            onClick = { navController.popBackStack() }
                        )
                    }

                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun PartyRow(role: String, name: String, id: String, icon: ImageVector, color: Color) {
    val appColors = LocalAppColors.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(22.dp)
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(role, style = MaterialTheme.typography.labelSmall, color = appColors.textTertiary)
            Text(
                name,
                style = MaterialTheme.typography.titleSmall,
                color = appColors.textPrimary,
                fontWeight = FontWeight.SemiBold
            )
            Text(id, style = MaterialTheme.typography.bodySmall, color = appColors.textSecondary)
        }
    }
}
