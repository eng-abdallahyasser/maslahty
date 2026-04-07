package com.abdallahyasser.maslahty.presentaion.auth

import android.R.attr.text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abdallahyasser.maslahty.R
import com.abdallahyasser.maslahty.theme.CardBackground
import com.abdallahyasser.maslahty.theme.GoldenYellow
import com.abdallahyasser.maslahty.theme.HeaderGradientEnd
import com.abdallahyasser.maslahty.theme.HeaderGradientStart
import java.time.format.TextStyle


@Composable
fun LoginScreen(modifier: Modifier = Modifier) {


    Column() {

                LoginHeader()
                LoginBody()

    }
}

@Composable
fun LoginBody() {
    var nationalId by remember { mutableStateOf("") }
    var carName by remember { mutableStateOf("") }
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
        EditTextLogin(
            value = nationalId,
            onValueChange = { nationalId = it },
            label = "الرقم القومي",
            placeholder = "أدخل الرقم القومي...",
            imageVector = ImageVector.vectorResource(id = R.drawable.id),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // EditText الثاني - اسم السيارة
        EditTextLogin(
            value = carName,
            onValueChange = { carName = it },
            label = "رقم الهاتف",
            placeholder = "أدخل رقم الهاتف...",
            imageVector = ImageVector.vectorResource(id = R.drawable.phone)
        )

        Spacer(Modifier.height(24.dp))

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
                text = "تسجيل الدخول",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth() // عشان النص يكون في المنتصف
            )

        }


        Text(
            text = "مستخدم جديد؟ إنشاء حساب ",
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally),
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
            .height(350.dp)
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

@Composable
fun EditTextLogin(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    imageVector: ImageVector,
    modifier: Modifier = Modifier
) {

    Box(modifier = modifier) {

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            placeholder = { Text(placeholder) },
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = imageVector,
                    contentDescription = null,
                    tint = Color.Black,


                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )
    }

}