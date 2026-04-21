package com.abdallahyasser.maslahty.presentation.vechicle.Pricing

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Login
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.abdallahyasser.maslahty.presentation.navigation.Route
import com.abdallahyasser.maslahty.theme.AppColors
import com.abdallahyasser.maslahty.theme.LocalAppColors
import com.example.maslahty.presentation.components.PrimaryButton
import com.example.maslahty.presentation.components.StepIndicator

@Composable
fun PricingScreen(
    navController: NavHostController,
    vehicleId: String,
) {
    val viewModel: PricingViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()
    val appColors = LocalAppColors.current

    LaunchedEffect(vehicleId) {
        viewModel.loadMarketPrice(vehicleId)
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF1A233A))
                        .padding(top = 16.dp, bottom = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Spacer(modifier = Modifier.size(40.dp))
                        Text(
                            "بيع مركبة",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.Default.ArrowForward,
                                contentDescription = "Back",
                                tint = Color.White,
                                modifier = Modifier.background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                        StepIndicator(
                            currentStep = 3,
                            totalSteps = 4,
                            stepLabels = listOf("المركبة", "الصور", "السعر", "الإرسال")
                        )
                    }
                }
            },
            bottomBar = {
                if (state.error != null) {
                    Text(
                        text = state.error!!,
                        color = Color.Red,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                PrimaryButton(
                    text = "التالي - مراجعة البيانات",
                    icon = Icons.Default.ArrowBack,
                    modifier = Modifier.padding(20.dp).fillMaxWidth(),
                    onClick = {
                        viewModel.onNextClicked(vehicleId) {
                            navController.navigate(Route.TransferRequestRoute(vehicleId))
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFFF8F9FA))
                    .verticalScroll(rememberScrollState())
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                "تحديد السعر",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1A233A)
                            )
                            Spacer(Modifier.width(8.dp))
                            Box(modifier = Modifier.width(4.dp).height(24.dp).background(appColors.gold, RoundedCornerShape(2.dp)))
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // تم تعديل التمرير هنا ليعمل الـ ViewModel
                        CustomPriceInput(
                            value = state.salePriceInput,
                            onValueChange = { viewModel.onPriceChange(it) }, // الإصلاح هنا
                            appColors = appColors
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        MarketPriceIndicator(
                            marketPrice = state.marketPrice,
                            appColors = appColors
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CustomPriceInput(value: String, onValueChange: (String) -> Unit, appColors: AppColors) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .border(1.5.dp, appColors.gold, RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("ج.م", fontWeight = FontWeight.Bold, color = Color.Gray, fontSize = 16.sp)
            Text("جنيه", fontSize = 10.sp, color = Color.Gray)
        }

        Spacer(modifier = Modifier.width(16.dp))
        Box(modifier = Modifier.width(1.dp).fillMaxHeight(0.6f).background(Color.LightGray))
        Spacer(modifier = Modifier.width(16.dp))

        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text("أدخل السعر المطلوب", color = Color.LightGray, textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth()) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            // تم تكبير الخط هنا ليكون أوضح
            textStyle = TextStyle(
                fontSize = 20.sp,
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )
    }
}

@Composable
fun MarketPriceIndicator(marketPrice: Double, appColors: AppColors) {
    val avg = marketPrice
    val min = avg * 0.85
    val max = avg * 1.15

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(12.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "مؤشر سعر السوق",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A233A),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.height(40.dp))

        Box(modifier = Modifier.fillMaxWidth().height(80.dp), contentAlignment = Alignment.Center) {
            // الخط الرمادي الخلفي (سُمك أكبر 15dp)
            Box(modifier = Modifier.fillMaxWidth(0.8f).height(15.dp).background(Color(0xFFE9ECEF), RoundedCornerShape(4.dp)))

            // الخط الذهبي (سُمك أكبر 15dp)
            Box(modifier = Modifier.fillMaxWidth(0.4f).height(15.dp).background(appColors.gold))

            Row(
                modifier = Modifier.fillMaxWidth(0.85f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PriceTick("أقل", min)
                PriceTick("متوسط", avg)
                PriceTick("أعلى", max)
            }

            // أيقونة المؤشر (مرفوعة قليلاً لتناسب السُمك الجديد)
            Box(modifier = Modifier.offset(y = (-40).dp)) {
                Icon(
                    Icons.Default.Login,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(45.dp)
                        .background(Color(0xFF1A233A), RoundedCornerShape(8.dp))
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun PriceTick(label: String, price: Double) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.size(2.dp, 12.dp).background(Color.Black))
        Spacer(modifier = Modifier.height(8.dp))
        Text(label, fontSize = 12.sp, color = Color.Gray)
        Text("${"%,.0f".format(price)}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}