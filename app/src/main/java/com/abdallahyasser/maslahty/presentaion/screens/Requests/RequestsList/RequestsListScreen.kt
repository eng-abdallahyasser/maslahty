package com.abdallahyasser.maslahty.presentaion.screens.Requests.RequestsList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.abdallahyasser.maslahty.presentaion.screens.home.HomeViewModel
import com.abdallahyasser.maslahty.theme.LocalAppColors
import com.example.maslahty.domain.entities.TransferRequest



@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val appColors = LocalAppColors.current
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = appColors.cardBackground),
        border = androidx.compose.foundation.BorderStroke(1.dp, appColors.cardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        )
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

private data class Quad<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
@Composable
fun StatusBadge(
    status: String,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current
    val (bgColor, textColor, label, icon) = when (status.uppercase()) {
        "PENDING" -> Quad(
            appColors.statusPendingBg, appColors.statusPending,
            "قيد الانتظار", Icons.Default.Schedule
        )

        "APPROVED_BY_BUYER" -> Quad(
            appColors.statusApprovedBg, appColors.statusApproved,
            "موافق المشتري", Icons.Default.CheckCircle
        )

        "COMPLETED" -> Quad(
            appColors.statusCompletedBg, appColors.statusCompleted,
            "مكتمل", Icons.Default.Verified
        )

        "REJECTED_BY_BUYER" -> Quad(
            appColors.statusRejectedBg, appColors.statusRejected,
            "مرفوض", Icons.Default.Cancel
        )

        else -> Quad(
            appColors.cardBackground, appColors.textSecondary,
            status, Icons.Default.Info
        )
    }
}

@Composable
fun GradientHeader(
    title: String,
    subtitle: String = "",
    onBack: (() -> Unit)? = null,
    icon: ImageVector? = null
) {
    val appColors = LocalAppColors.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.linearGradient(
                    colors = listOf(appColors.navy, appColors.gradientEnd.copy(alpha = 0.9f))
                )
            )
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            if (onBack != null) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.12f))
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "رجوع",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            if (icon != null) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(appColors.gold.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = appColors.gold,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                if (subtitle.isNotEmpty()) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null
) {
    val appColors = LocalAppColors.current
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(22.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(appColors.gold)
        )
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = appColors.textPrimary,
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
}

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    leadingIcon: ImageVector? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    enabled: Boolean = true,
    isError: Boolean = false,
    readOnly: Boolean = false
) {
    val appColors = LocalAppColors.current
    val isFocused = remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = appColors.textSecondary,
            modifier = Modifier.padding(bottom = 6.dp, start = 2.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                if (placeholder.isNotEmpty())
                    Text(placeholder, color = appColors.textTertiary, fontSize = 14.sp)
            },
            leadingIcon = leadingIcon?.let {
                {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = if (isError) MaterialTheme.colorScheme.error
                        else appColors.gold.copy(alpha = 0.8f),
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            trailingIcon = trailingIcon,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = visualTransformation,
            singleLine = singleLine,
            maxLines = maxLines,
            enabled = enabled,
            readOnly = readOnly,
            isError = isError,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = appColors.gold,
                unfocusedBorderColor = appColors.cardBorder,
                errorBorderColor = MaterialTheme.colorScheme.error,
                focusedContainerColor = appColors.cardBackground,
                unfocusedContainerColor = appColors.cardBackground,
                disabledContainerColor = appColors.cardBackground.copy(alpha = 0.6f),
                focusedTextColor = appColors.textPrimary,
                unfocusedTextColor = appColors.textPrimary,
                cursorColor = appColors.gold
            )
        )
    }
}

/*@Composable
fun RequestsManagementScreen(
    navController: NavHostController) {
    val appColors = LocalAppColors.current
    val requestsListViewModel: RequestsListViewModel = viewModel()
    val uiState=requestsListViewModel.screenState.collectAsState()

//    var buyerIdOrNationalId by remember { mutableStateOf("9876543210") }
//    val requestsState by viewModel.RequestsListState.collectAsState()

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
                                    onClick = { navController.navigate(Screen.RequestDetailsScreen.createRoute(request.id)) }
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
}*/

@Composable
fun RequestsManagementScreen(
    navController: NavHostController,
    val requestsListViewModel: RequestsListViewModel = viewModel()
) {
    val appColors = LocalAppColors.current
    val requestsState by viewModel.buyerRequestsState.collectAsState()

    // 1. Automatically fetch requests when the screen loads
    LaunchedEffect(Unit) {
        viewModel.getBuyerRequests("") // Pass empty or a default ID if required by your API
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {

            GradientHeader(
                title = "إدارة الطلبات",
                subtitle = "عرض وإدارة طلبات نقل الملكية",
                onBack = { navController.popBackStack() },
                icon = Icons.Default.Assignment
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 2. We removed the SectionHeader and Row containing the TextField/Button

                when (val state = requestsState) {
                    is TransferState.Loading -> {
                        LoadingBox(message = "جاري تحميل الطلبات...")
                    }

                    is TransferState.Error -> {
                        ErrorMessage(state.message)
                    }

                    is TransferState.RequestsLoaded -> {
                        TransferDraftStore.lastLoadedRequests = state.requests

                        if (state.requests.isEmpty()) {
                            // 3. Show "No requests found" if list is empty
                            Box(
                                modifier = Modifier.fillMaxSize().padding(top = 60.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        Icons.Default.Inbox,
                                        null,
                                        tint = appColors.textTertiary,
                                        modifier = Modifier.size(64.dp)
                                    )
                                    Spacer(Modifier.height(16.dp))
                                    Text(
                                        "لا توجد طلبات حالياً",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = appColors.textSecondary
                                    )
                                }
                            }
                        } else {
                            // 4. Show the RequestCards directly
                            Text(
                                "قائمة الطلبات (${state.requests.size})",
                                style = MaterialTheme.typography.titleSmall,
                                color = appColors.textSecondary,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            state.requests.forEach { request ->
                                RequestCard(
                                    request = request,
                                    onClick = {
                                        navController.navigate(Screen.RequestDetailsScreen.createRoute(request.id))
                                    }
                                )
                            }
                        }
                    }
                    // Handle any other states (like Initial) as a loading or empty state
                    else -> {
                        LoadingBox(message = "جاري الاتصال...")
                    }
                }
            }
        }
    }
}