package com.abdallahyasser.maslahty.presentation.transfer.success

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.abdallahyasser.maslahty.presentation.navigation.Route
import com.abdallahyasser.maslahty.theme.LocalAppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferDecisionResultScreen(
    navController: NavHostController,
    requestId: String,
    isAccepted: Boolean,
    viewModel: TransferDecisionResultViewModel = hiltViewModel()
) {
    val appColors = LocalAppColors.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(requestId) {
        viewModel.loadDetails(requestId)
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                // Customized Header as shown in the screenshot
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .background(Color(0xFF0D1B3E)) // Dark Navy
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Right side in RTL (Start): Menu Icon in Yellow
                    IconButton(
                        onClick = { /* Menu Action */ },
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "القائمة",
                            tint = Color(0xFFFDC003),
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    // Left side in RTL (End): Title "توثيق بلس" in Yellow
                    Text(
                        text = "توثيق بلس",
                        color = Color(0xFFFDC003),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF8F9FA))
                    .padding(innerPadding)
            ) {
                when (val state = uiState) {
                    is DecisionResultState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color(0xFFFDC003))
                        }
                    }
                    is DecisionResultState.Error -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = state.message,
                                color = Color.Red,
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { viewModel.loadDetails(requestId) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D1B3E))
                            ) {
                                Text("إعادة المحاولة", color = Color.White)
                            }
                        }
                    }
                    is DecisionResultState.Success -> {
                        val request = state.request
                        val vehicle = state.vehicle
                        val vehicleModelName = vehicle?.model ?: request.vehicleId

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(horizontal = 24.dp, vertical = 24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            Spacer(modifier = Modifier.height(8.dp))

                            // 1. Result Graphic Circle Illustration
                            Box(
                                modifier = Modifier.size(170.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                // Outer Ring
                                Box(
                                    modifier = Modifier
                                        .size(130.dp)
                                        .border(
                                            width = 3.5.dp,
                                            color = if (isAccepted) Color(0xFFFDC003) else Color(0xFFEF4444),
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    // Inner Circle
                                    Box(
                                        modifier = Modifier
                                            .size(76.dp)
                                            .clip(CircleShape)
                                            .background(if (isAccepted) Color(0xFFFDC003) else Color(0xFFEF4444)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = if (isAccepted) Icons.Default.Check else Icons.Default.Close,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(42.dp)
                                        )
                                    }
                                }

                                // Verified/Cancel Badge (Top-Right in RTL -> TopStart with offset)
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopStart)
                                        .offset(x = 24.dp, y = 14.dp)
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF0D1B3E))
                                        .border(1.5.dp, Color(0xFFFDC003), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = if (isAccepted) Icons.Default.Verified else Icons.Default.Cancel,
                                        contentDescription = null,
                                        tint = if (isAccepted) Color(0xFFFDC003) else Color(0xFFEF4444),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // 2. Large status title
                            Text(
                                text = if (isAccepted) "تم قبول الطلب بنجاح" else "تم رفض الطلب",
                                fontSize = 26.sp,
                                color = Color(0xFF0D1B3E),
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            // 3. Subtext
                            Text(
                                text = if (isAccepted) {
                                    "لقد تم قبول طلب نقل الملكية من قبل الطرف الآخر. يمكنك الآن متابعة الخطوات النهائية لإتمام العملية."
                                } else {
                                    "لقد تم رفض طلب نقل الملكية. يمكنك العودة للصفحة الرئيسية وإعادة المحاولة."
                                },
                                fontSize = 15.sp,
                                color = Color(0xFF7684A1),
                                lineHeight = 22.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )

                            Spacer(modifier = Modifier.height(28.dp))

                            // 4. Request Details Card
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(24.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                                border = BorderStroke(1.dp, Color(0xFFE9ECEF))
                            ) {
                                Column {
                                    Column(
                                        modifier = Modifier.padding(20.dp),
                                        verticalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        // Request Status Row
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "حالة الطلب",
                                                color = Color(0xFF7684A1),
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 15.sp
                                            )
                                            // Status Badge
                                            Surface(
                                                shape = RoundedCornerShape(30.dp),
                                                color = if (isAccepted) Color(0xFFDCFCE7) else Color(0xFFFEE2E2),
                                                border = BorderStroke(
                                                    0.5.dp,
                                                    if (isAccepted) Color(0xFFBBF7D0) else Color(0xFFFCA5A5)
                                                )
                                            ) {
                                                Row(
                                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = if (isAccepted) Icons.Default.Check else Icons.Default.Close,
                                                        contentDescription = null,
                                                        tint = if (isAccepted) Color(0xFF15803D) else Color(0xFFB91C1C),
                                                        modifier = Modifier.size(14.dp)
                                                    )
                                                    Text(
                                                        text = if (isAccepted) "مقبول" else "مرفوض",
                                                        color = if (isAccepted) Color(0xFF15803D) else Color(0xFFB91C1C),
                                                        fontSize = 13.sp,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                }
                                            }
                                        }

                                        // Request Number Row
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .clip(RoundedCornerShape(10.dp))
                                                    .background(Color(0xFFF1F3F5)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.ReceiptLong,
                                                    contentDescription = null,
                                                    tint = Color(0xFF0D1B3E),
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }
                                            Column {
                                                Text(
                                                    text = "رقم الطلب",
                                                    color = Color(0xFF7684A1),
                                                    fontSize = 12.sp
                                                )
                                                Text(
                                                    text = "#${request.id.takeLast(8)}",
                                                    color = Color(0xFF0D1B3E),
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 15.sp
                                                )
                                            }
                                        }

                                        // Vehicle Row
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .clip(RoundedCornerShape(10.dp))
                                                    .background(Color(0xFFF1F3F5)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.DirectionsCar,
                                                    contentDescription = null,
                                                    tint = Color(0xFF0D1B3E),
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }
                                            Column {
                                                Text(
                                                    text = "المركبة",
                                                    color = Color(0xFF7684A1),
                                                    fontSize = 12.sp
                                                )
                                                Text(
                                                    text = vehicleModelName,
                                                    color = Color(0xFF0D1B3E),
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 15.sp
                                                )
                                            }
                                        }
                                    }

                                    // Yellow decorative line above image as seen in screenshot
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(3.dp)
                                            .background(Color(0xFFFDC003))
                                    )

                                    // Car Image
                                    AsyncImage(
                                        model = vehicle?.vehicleImageUrl ?: "https://images.unsplash.com/photo-1617788138017-80ad40651399?q=80&w=600",
                                        contentDescription = "صورة المركبة",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(180.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(36.dp))

                            // 5. Action Buttons
                            if (isAccepted) {
                                // Complete transfer button (Yellow)
                                Button(
                                    onClick = {
                                        navController.navigate(Route.Home) {
                                            popUpTo(Route.Home) { inclusive = true }
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp),
                                    shape = RoundedCornerShape(14.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFFDC003),
                                        contentColor = Color(0xFF0D1B3E)
                                    ),
                                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                                ) {
                                    Text(
                                        text = "إتمام عملية النقل",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color(0xFF0D1B3E)
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                // Return to home button (Bordered / White)
                                OutlinedButton(
                                    onClick = {
                                        navController.navigate(Route.Home) {
                                            popUpTo(Route.Home) { inclusive = true }
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp),
                                    shape = RoundedCornerShape(14.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = Color(0xFF0D1B3E)
                                    ),
                                    border = BorderStroke(1.dp, Color(0xFFADB5CC))
                                ) {
                                    Text(
                                        text = "العودة للرئيسية",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color(0xFF0D1B3E)
                                    )
                                }
                            } else {
                                // For refusal state: just Return to home button (Yellow)
                                Button(
                                    onClick = {
                                        navController.navigate(Route.Home) {
                                            popUpTo(Route.Home) { inclusive = true }
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp),
                                    shape = RoundedCornerShape(14.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFFDC003),
                                        contentColor = Color(0xFF0D1B3E)
                                    ),
                                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                                ) {
                                    Text(
                                        text = "العودة للرئيسية",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color(0xFF0D1B3E)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
