package com.abdallahyasser.maslahty.presentation.my_vehicle

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
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
import com.abdallahyasser.maslahty.domain.vehicle.entity.Vehicle
import com.abdallahyasser.maslahty.presentation.navigation.Route
import com.abdallahyasser.maslahty.theme.LocalAppColors

@Composable
fun MyVehicleScreen(
    navController: NavHostController,
    viewModel: MyVehicleViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val appColors = LocalAppColors.current

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { 
                        // Navigate to add vehicle screen (to be implemented or existing)
                        navController.navigate(Route.VehicleDetails) 
                    },
                    containerColor = appColors.gold,
                    contentColor = appColors.navy,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Add, contentDescription = "إضافة مركبة")
                }
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(appColors.navy, appColors.gradientEnd)
                            )
                        )
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            IconButton(
                                onClick = { navController.popBackStack() },
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.1f))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "رجوع",
                                    tint = Color.White
                                )
                            }
                            Text(
                                text = "مركباتي",
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "إدارة جميع المركبات الخاصة بك في مكان واحد",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }

                if (state.isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = appColors.gold)
                    }
                } else if (state.error != null) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Info, contentDescription = null, tint = Color.Red, modifier = Modifier.size(48.dp))
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = state.error, color = Color.Red)
                            Button(onClick = { viewModel.loadVehicles() }, colors = ButtonDefaults.buttonColors(containerColor = appColors.gold)) {
                                Text("إعادة المحاولة", color = appColors.navy)
                            }
                        }
                    }
                } else if (state.vehicles.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.DirectionsCar, contentDescription = null, tint = appColors.textSecondary, modifier = Modifier.size(80.dp))
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "لا توجد مركبات مسجلة", style = MaterialTheme.typography.titleMedium, color = appColors.textSecondary)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "قم بإضافة مركبتك الأولى الآن", style = MaterialTheme.typography.bodySmall, color = appColors.textSecondary.copy(alpha = 0.6f))
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.vehicles) { vehicle ->
                            VehicleCard(vehicle = vehicle)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun VehicleCard(vehicle: Vehicle) {
    val appColors = LocalAppColors.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { /* Detail navigation if needed */ },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Vehicle Icon
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(appColors.navy.copy(alpha = 0.05f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.DirectionsCar,
                    contentDescription = null,
                    tint = appColors.navy,
                    modifier = Modifier.size(30.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = vehicle.model,
                    style = MaterialTheme.typography.titleMedium,
                    color = appColors.textPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "سنة الصنع: ${vehicle.manufacturingYear}",
                    style = MaterialTheme.typography.bodySmall,
                    color = appColors.textSecondary
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // License Plate Tag
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(appColors.gold.copy(alpha = 0.1f))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = vehicle.licensePlate,
                        style = MaterialTheme.typography.labelMedium,
                        color = appColors.gold,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Status or Arrow
            Icon(
                imageVector = Icons.Default.ArrowBack, // In RTL this points left, we might want forward
                contentDescription = null,
                tint = appColors.textSecondary.copy(alpha = 0.4f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
