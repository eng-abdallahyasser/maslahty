package com.abdallahyasser.maslahty.presentaion.view.SplashScreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abdallahyasser.maslahty.R
import com.abdallahyasser.maslahty.theme.DarkNavy
import com.abdallahyasser.maslahty.theme.DarkNavyLight
import com.abdallahyasser.maslahty.theme.GoldenYellow
import com.abdallahyasser.maslahty.theme.GrayBlue
import com.abdallahyasser.maslahty.theme.LightGray
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit = {}
) {
    // ---- Animations ----
    val iconScale = remember { Animatable(0f) }
    val iconRotation = remember { Animatable(-30f) }
    val titleAlpha = remember { Animatable(0f) }
    val titleOffsetY = remember { Animatable(20f) }
    val subtitleAlpha = remember { Animatable(0f) }
    val bottomAlpha = remember { Animatable(0f) }
    val bottomOffsetY = remember { Animatable(30f) }

    // ---- Dot animation state ----
    // النقطة النشطة بتتحرك بين 0, 1, 2 (من اليمين لليسار)
    var activeDotIndex by remember { mutableIntStateOf(0) }

    // Dot cycling animation - بتلف على النقط الـ 3
    LaunchedEffect(Unit) {
        // نستنى لحد ما الأنيميشن الأولية تخلص
        delay(1200)
        // بعد كده نبدأ نحرك النقط
        while (true) {
            delay(400)
            activeDotIndex = (activeDotIndex + 1) % 3
        }
    }

    LaunchedEffect(Unit) {
        // Icon spring animation
        launch {
            iconScale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
        launch {
            iconRotation.animateTo(
                targetValue = 12f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }

        // Title fade in after short delay
        delay(300)
        launch {
            titleAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing)
            )
        }
        launch {
            titleOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing)
            )
        }

        // Subtitle fade in
        delay(200)
        launch {
            subtitleAlpha.animateTo(
                targetValue = 0.8f,
                animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
            )
        }

        // Bottom section
        delay(200)
        launch {
            bottomAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            )
        }
        launch {
            bottomOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            )
        }

        // Wait for loading then navigate to HomeScreen
        delay(3000)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(DarkNavy, DarkNavyLight),
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 600f)
                )
            )
    ) {
        // ---- Radial gradient overlay ----
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            DarkNavy.copy(alpha = 0f),
                            DarkNavy.copy(alpha = 0.4f)
                        ),
                        center = Offset(Float.POSITIVE_INFINITY / 2, Float.POSITIVE_INFINITY / 2),
                        radius = 900f
                    )
                )
        )

        // ---- Decorative blur circles ----
        // Golden blur circle (top area)
        Box(
            modifier = Modifier
                .offset(x = 45.dp, y = (-88).dp)
                .size(384.dp)
                .blur(50.dp)
                .background(
                    color = GoldenYellow.copy(alpha = 0.05f),
                    shape = CircleShape
                )
        )

        // White blur circle (bottom-left area)
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = (-20).dp, y = 40.dp)
                .size(288.dp)
                .blur(40.dp)
                .background(
                    color = Color.White.copy(alpha = 0.05f),
                    shape = CircleShape
                )
        )

        // ---- Center content ----
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Icon - rotated golden rounded square
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = iconScale.value
                        scaleY = iconScale.value
                        rotationZ = iconRotation.value
                    }
                    .size(128.dp)
                    .shadow(
                        elevation = 25.dp,
                        shape = RoundedCornerShape(40.dp),
                        ambientColor = GoldenYellow.copy(alpha = 0.4f),
                        spotColor = GoldenYellow.copy(alpha = 0.4f)
                    )
                    .background(
                        color = GoldenYellow,
                        shape = RoundedCornerShape(40.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                // App logo icon
                Image(
                    painter = painterResource(id = R.drawable.icon),
                    contentDescription = null,
                    modifier = Modifier
                        .graphicsLayer {
                            rotationZ = -iconRotation.value
                        }
                        .size(width = 80.dp, height = 80.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // App name in Arabic "مصلحتي"
            Text(
                text = "مصلحتي",
                color = GoldenYellow,
                fontSize = 48.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                lineHeight = 48.sp,
                modifier = Modifier.graphicsLayer {
                    alpha = titleAlpha.value
                    translationY = titleOffsetY.value
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // English subtitle "MASLAHATI"
            Text(
                text = "MASLAHATI",
                color = GrayBlue,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                letterSpacing = 1.8.sp,
                lineHeight = 28.sp,
                modifier = Modifier.graphicsLayer {
                    alpha = subtitleAlpha.value
                }
            )
        }

        // ---- Bottom section ----
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .graphicsLayer {
                    alpha = bottomAlpha.value
                    translationY = bottomOffsetY.value
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Animated page indicator dots
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(3) { index ->
                    val isActive = index == activeDotIndex
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .then(
                                if (isActive) {
                                    Modifier.shadow(
                                        elevation = 12.dp,
                                        shape = CircleShape,
                                        ambientColor = GoldenYellow.copy(alpha = 0.6f),
                                        spotColor = GoldenYellow.copy(alpha = 0.6f)
                                    )
                                } else {
                                    Modifier
                                }
                            )
                            .background(
                                color = if (isActive) GoldenYellow else GrayBlue.copy(alpha = 0.3f),
                                shape = CircleShape
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(21.dp))

            // Thin divider line
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(1.dp)
                    .background(LightGray.copy(alpha = 0.2f))
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Bottom tagline in Arabic
            Text(
                text = "بوابة نقل ملكية المركبات ",
                color = GrayBlue.copy(alpha = 0.6f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Right,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}
