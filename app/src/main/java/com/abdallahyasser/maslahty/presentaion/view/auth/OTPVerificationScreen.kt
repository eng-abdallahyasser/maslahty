package com.abdallahyasser.maslahty.presentaion.view.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.abdallahyasser.maslahty.theme.GoldenYellow
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abdallahyasser.maslahty.R


@Composable
fun OTPVerification(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {
        navController.popBackStack()
    },
    onConfirm: (String) -> Unit = {},
    phoneNumber: String
) {
    val vm: AuthViewModel= viewModel(factory = AuthViewModelFactory())

    val state = vm.authState.collectAsState()

    Column(
        modifier = modifier
            .background(Color(0xFF121A35))
            .fillMaxWidth()
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFF374151), shape = RoundedCornerShape(12.dp))
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.der3),
                    contentDescription = "Info",
                    tint = GoldenYellow,
                    modifier = Modifier.size(24.dp)
                )
            }


            Text(
                text = "التحقق من الرمز",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Right
            )

            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFF374151), shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }


        }

        // Main Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFFF8F9FC),
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
                .padding(horizontal = 24.dp, vertical = 40.dp)
        ) {
            // Title and Description
            Text(
                text = "إدخال رمز التحقق",
                color = Color(0xFF111827),
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 11.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = "تم إرسال رمز مكون من 4 أرقام إلى رقم هاتفك 01xxxxxxxxx",
                color = Color(0xFF4B5563),
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp),
                textAlign = TextAlign.Center
            )

            // OTP Input Fields
            BasicTextField(
                value = state.value.otpValue,
                onValueChange = { newValue ->
                    // Only allow digits and max 4 characters
                    if (newValue.all { it.isDigit() } && newValue.length <= 4) {
                        vm.updateOtpValue(newValue)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp),
                decorationBox = { _ ->
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            repeat(4) { index ->
                                OTPInputBox(
                                    value = if (index < state.value.otpValue.length) state.value.otpValue[index].toString() else "",
                                    modifier = Modifier
                                        .size(70.dp)
                                        .padding(horizontal = 8.dp)
                                )
                            }
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // Timer Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = state.value.timeRemaining,
                    color = Color(0xFF1F2937),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "إعادة إرسال الرمز خلال",
                    color = Color(0xFF1F2937),
                    fontSize = 14.sp
                )
            }

            // Resend Link
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "لم تتلق الرمز؟",
                    color = Color(0xFF1F2937),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "إعادة الإرسال",
                    color = Color(0xFF9CA3AF),
                    fontSize = 14.sp,
                    modifier = Modifier
                )
            }

            Spacer(modifier = Modifier.height(236.dp))

            // Confirm Button
            Button(
                onClick = { onConfirm(state.value.otpValue) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GoldenYellow
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "تأكيد",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun OTPInputBox(
    value: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        if (value.isEmpty()) {
            Text(
                text = "",
                color = Color(0xFF111827),
                fontSize = 50.sp,
                fontWeight = FontWeight.Normal
            )
        } else {
            Text(
                text = "•",
                color = Color(0xFF111827),
                fontSize = 50.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}
