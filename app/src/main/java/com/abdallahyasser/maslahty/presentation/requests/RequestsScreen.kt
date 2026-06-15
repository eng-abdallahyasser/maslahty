package com.abdallahyasser.maslahty.presentation.requests

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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.abdallahyasser.maslahty.domain.transfer.entity.TransferRequest
import com.abdallahyasser.maslahty.domain.vehicle.entity.Vehicle
import com.abdallahyasser.maslahty.presentation.navigation.Route
import com.abdallahyasser.maslahty.theme.LocalAppColors
import com.example.maslahty.presentation.components.AppCard
import com.example.maslahty.presentation.components.AppTextField
import com.example.maslahty.presentation.components.ErrorMessage
import com.example.maslahty.presentation.components.GradientHeader
import com.example.maslahty.presentation.components.LoadingBox
import com.example.maslahty.presentation.components.SectionHeader
import com.example.maslahty.presentation.components.StatusBadge

@Composable
fun RequestsScreen(
    navController: NavHostController,
    viewModel: RequestsViewModel = hiltViewModel()
) {
    val appColors = LocalAppColors.current
    var buyerIdOrNationalId by remember { mutableStateOf("9876543210") }
    val requestsState by viewModel.buyerRequestsState.collectAsState()



    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {

            GradientHeader(
                title = "إدارة الطلبات",
                subtitle = "عرض وإدارة طلبات نقل الملكية",
                onBack = { navController.popBackStack() },
                icon = Icons.Default.Assignment
            )

            Column(
                modifier = Modifier.fillMaxWidth().weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SectionHeader(title = "البحث بالرقم القومي", icon = Icons.Default.Search)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    AppTextField(
                        value = buyerIdOrNationalId,
                        onValueChange = { buyerIdOrNationalId = it.trim() },
                        label = "الرقم القومي للمشتري",
                        leadingIcon = Icons.Default.Badge,
                        modifier = Modifier.weight(1f)
                    )
                    Button(
                        onClick = { viewModel.getBuyerRequests(buyerIdOrNationalId) },
                        modifier = Modifier.height(56.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = appColors.navy)
                    ) {
                        Icon(Icons.Default.Search, null, Modifier.size(20.dp))
                    }
                }

                when (val state = requestsState) {
                    is TransferState.Loading -> LoadingBox(message = "جاري تحميل الطلبات...")
                    is TransferState.Error -> ErrorMessage(state.message)
                    is TransferState.RequestsLoaded -> {
                        TransferDraftStore.lastLoadedRequests = state.requests
                        if (state.requests.isEmpty()) {
                            Box(modifier = Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Default.Inbox, null, tint = appColors.textTertiary, modifier = Modifier.size(52.dp))
                                    Spacer(Modifier.height(12.dp))
                                    Text("لا توجد طلبات حالياً", style = MaterialTheme.typography.titleMedium, color = appColors.textSecondary)
                                }
                            }
                        } else {
                            Text(
                                "${state.requests.size} طلب",
                                style = MaterialTheme.typography.titleSmall,
                                color = appColors.textSecondary
                            )
                            state.requests.forEach { request ->
                                RequestCard(
                                    request = request,
                                    onClick = { navController.navigate(Route.RequestDetailsScreen(requestId = request.id)) }
                                )
                            }
                        }
                    }
                    else -> Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Search, null, tint = appColors.textTertiary, modifier = Modifier.size(44.dp))
                            Spacer(Modifier.height(8.dp))
                            Text("اضغط بحث لعرض الطلبات", style = MaterialTheme.typography.bodyMedium, color = appColors.textSecondary)
                    }
                }

            }
            }
        }
    }
}



@Composable
private fun RequestCard(request: TransferRequest, onClick: () -> Unit) {
    val appColors = LocalAppColors.current
    AppCard(modifier = Modifier.clickable(onClick = onClick)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
            Column(modifier = Modifier.weight(1f)) {
                Text("طلب #${request.id.takeLast(6)}", style = MaterialTheme.typography.titleSmall, color = appColors.textPrimary, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                Text("EGP ${"%.0f".format(request.price)}", style = MaterialTheme.typography.bodyMedium, color = appColors.gold, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(4.dp))
                Text("البائع: ${request.sellerName}", style = MaterialTheme.typography.bodySmall, color = appColors.textSecondary)
            }
            Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                StatusBadge(status = request.status.name)
                Icon(Icons.Default.ChevronLeft, null, tint = appColors.textTertiary, modifier = Modifier.size(20.dp))
            }
        }
    }
}

private data class TransferDraft(
    val vehicle: Vehicle,
    val salePrice: Double? = null,
    val marketPrice: Double? = null,
    val notes: String = "",
    val buyerNationalId: String = ""
)

private object TransferDraftStore {
    val drafts = mutableStateMapOf<String, TransferDraft>()
    var lastLoadedRequests: List<TransferRequest> = emptyList()
}
