package com.abdallahyasser.maslahty.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigator
import com.abdallahyasser.maslahty.R
import com.abdallahyasser.maslahty.presentation.navigation.Route
import com.abdallahyasser.maslahty.presentation.shared_composables.CustomEditText
import com.abdallahyasser.maslahty.theme.GoldenYellow
import com.abdallahyasser.maslahty.theme.HeaderGradientEnd
import com.abdallahyasser.maslahty.theme.HeaderGradientStart
import kotlinx.coroutines.flow.collectLatest


@Composable
fun LoginScreen(navController: NavController) {


    Column() {

        LoginHeader()
        LoginBody(navController)

    }
}

@Composable
fun LoginBody(navController: NavController) {

    val vm: AuthViewModel = hiltViewModel()

    val state = vm.authState.collectAsState().value

    LaunchedEffect(Unit) {
        vm.eventFlow.collectLatest { event ->
            if (event is AuthUiEvent.NavigateToVerifyOTP) {
                navController.navigate(Route.OTP)
            }
        }
    }



    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        Column() {
            Text(
                text = "تسجيل الدخول",
                modifier = Modifier.padding(bottom = 16.dp)
            )
            // EditText الأول - الرقم القومي
            CustomEditText(
                value = state.nationalId,
                onValueChange = { vm.updateNationalId(it) },
                label = "الرقم القومي",
                placeholder = "أدخل الرقم القومي...",
                imageVector = ImageVector.vectorResource(id = R.drawable.id),
                modifier = Modifier.padding(bottom = 16.dp)
            )


            CustomEditText(
                value = state.phoneNumber,
                onValueChange = { vm.updatePhoneNumber(it) },
                label = "رقم الهاتف",
                placeholder = "أدخل رقم الهاتف...",
                imageVector = ImageVector.vectorResource(id = R.drawable.phone)
            )
            Spacer(Modifier.height(16.dp))

            if (state.error != null) {
                Text(
                    state.error,
                    color = Color.Red,
                )
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    vm.login()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(16.dp)
                    ),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GoldenYellow,
                    contentColor = HeaderGradientStart
                ),
                contentPadding = PaddingValues(0.dp) // لضمان الـ alignment زي الـ CSS
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                    )
                } else {

                    Text(
                        text = "تسجيل الدخول",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth() // عشان النص يكون في المنتصف
                    )
                }

            }

            Text(
                text = "مستخدم جديد؟ إنشاء حساب ",
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable() {
                        navController.navigate(Route.Registration)
                    },
                color = GoldenYellow,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

        }


    }

}

@Composable
fun LoginHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 40.dp,
                    bottomEnd = 40.dp
                )
            )
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(HeaderGradientStart, HeaderGradientEnd),
                    start = Offset(0f, 0f),
                    end = Offset(800f, 800f)
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        val image = ImageVector.vectorResource(id = R.drawable.car_con)
        Image(
            imageVector = image,
            contentDescription = "App Icon",
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.Center)
        )
    }
}

