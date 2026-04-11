package com.abdallahyasser.maslahty.presentaion.view.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.abdallahyasser.maslahty.R

// ============== Circular Orbit Icon ==============
@Composable
public fun CircularOrbitIcon() {

    Box(
        /*       modifier = Modifier.size(192.dp),
               contentAlignment = Alignment.Center*/
    ) {
        Icon(
            painter = painterResource(id = R.drawable.backgroundshadow),
            contentDescription = "Car Icon",
            modifier = Modifier.size(150.dp),
            tint = Color.Unspecified // مهم عشان يحافظ على اللون الأصلي
        )
    }
    /*    Box(
            modifier = Modifier.size(192.dp),
            contentAlignment = Alignment.Center
        ) {
            // Outer orbit ring
            Box(
                modifier = Modifier
                    .size(192.dp)
                    .border(
                        width = 1.dp,
                        color = BorderColor.copy(alpha = 0.3f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.backgroundshadow),
                    contentDescription = "Vehicle Icon",
                    tint = Color.Black,
                    modifier = Modifier.size(120.dp)
                )

            }

            // Floating document badge (top-right of circle)
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 8.dp, y = 24.dp)
            ) {
                // Badge shadow + white ring
                Box(
                    modifier = Modifier
                        .size(40.dp, 44.dp)
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(24.dp),
                            ambientColor = Color.Black.copy(alpha = 0.1f),
                            spotColor = Color.Black.copy(alpha = 0.1f)
                        )
                        .border(
                            width = 4.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(24.dp)
                        )
                        .background(
                            color = GoldenYellow,
                            shape = RoundedCornerShape(24.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Document icon
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = GoldenDark,
                        modifier = Modifier.size(16.dp, 20.dp)
                    )
                }
            }

            // Decorative dot (top-right corner of orbit)
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-1).dp, y = 1.dp)
                    .size(12.dp)
                    .background(
                        color = IconBrown,
                        shape = CircleShape
                    )
            )
        }*/
}