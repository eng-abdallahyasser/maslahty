package com.abdallahyasser.maslahty.presentaion.view.auth


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavController
import com.abdallahyasser.maslahty.R
import com.abdallahyasser.maslahty.presentaion.view.CustomComponent.CustomEditText
import com.abdallahyasser.maslahty.theme.GoldenYellow
import com.abdallahyasser.maslahty.theme.HeaderGradientEnd
import com.abdallahyasser.maslahty.theme.HeaderGradientStart


@Composable
fun SignUpScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState) // This makes it scrollable
             ) {
            SignUpHeader()
            SignUpBody(navController)


        }

}

@Composable
fun SignUpHeader() {
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

            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.signup),
                contentDescription = "App Icon",
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.Center)


            )
            Column(
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .align(Alignment.BottomCenter)
                   ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "إنشاء حساب جديد",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                )

                Text(
                    text = "ادخل بياناتك الشخصية",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier


                )
            }
    }
}




@Composable
fun SignUpBody(navController: NavController) {
    var fullName by remember { mutableStateOf("") }
    var nationlaId by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        Column() {
          Row() {
              Icon(
                  imageVector = ImageVector.vectorResource(id = R.drawable.person),
                  contentDescription = null,
                  tint = Color.Black,
                  modifier = Modifier.size(20.dp)
              )
              Text(
                  text = "البيانات شخصية",
                  color = Color.Black,
                  fontSize = 18.sp,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(start = 8.dp)
              )
          }

            Text(
                text = "الاسم الكامل",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 12.dp)
            )

            CustomEditText(
                value = fullName,
                onValueChange = { fullName = it },
                label = "الاسم باللغة العربية",
                placeholder = "ادخل اسمك الكامل",
                imageVector = ImageVector.vectorResource(id = R.drawable.person),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text(
                text = "الرقم القومي",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding()
            )


            CustomEditText(
                value = nationlaId,
                onValueChange = { nationlaId = it },
                label = "الرقم القومي",
                placeholder = "14 رقم",
                imageVector = ImageVector.vectorResource(id = R.drawable.id),
            )

            Spacer(Modifier.height(32.dp))

            Row() {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.phone),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "معلومات الاتصال",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Text(
                text = "رقم الهاتف",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 12.dp)
            )

            CustomEditText(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = "رقم الهاتف",
                placeholder = "01xxxxxxxxx",
                imageVector = ImageVector.vectorResource(id = R.drawable.phone),
            )
            Text(
                text = "البريد الإلكتروني",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 12.dp)
            )
            CustomEditText(
                value = email,
                onValueChange = { email = it },
                label = "البريد الإلكتروني",
                placeholder = "example@email.com",
                imageVector = ImageVector.vectorResource(id = R.drawable.email))

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = { /* Action هنا */ },
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
                Text(
                    text = "انشاء حساب",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth() // عشان النص يكون في المنتصف
                )

            }


            Text(
                text = "لديك حساب بالفعل ؟ تسجيل الدخول ",
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable() {
                        navController.popBackStack()
                    },
                color = GoldenYellow,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,


            )




        }
    }
}


