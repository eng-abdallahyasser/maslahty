package com.abdallahyasser.maslahty.presentaion.view.onboarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.abdallahyasser.maslahty.R
import com.abdallahyasser.maslahty.presentaion.navigation.Route
import com.abdallahyasser.maslahty.theme.CardBackground
import com.abdallahyasser.maslahty.theme.DarkNavyLight
import com.abdallahyasser.maslahty.theme.GoldenDark
import com.abdallahyasser.maslahty.theme.GoldenYellow
import com.abdallahyasser.maslahty.theme.HeaderGradientEnd
import com.abdallahyasser.maslahty.theme.HeaderGradientStart
import com.abdallahyasser.maslahty.theme.IndicatorInactive
import com.abdallahyasser.maslahty.theme.IndicatorInactiveDot
import com.abdallahyasser.maslahty.theme.PageBackground
import com.abdallahyasser.maslahty.theme.TextDark
import com.abdallahyasser.maslahty.theme.TextGray
import com.abdallahyasser.maslahty.theme.TextGrayAlpha

// ============== Data Classes ==============
data class OnboardingFeature(
    val title: String,
    val description: String,
    val iconResId: Int? = null
)

data class OnboardingPageData(
    val headerTitle: String,
    val headerSubtitle: String,
    val cardTitle: String,
    val cardDescription: String,
    val features: List<OnboardingFeature>,
    val activeStep: Int // 0-indexed
)

// ============== Main Onboarding Screen ==============
@Composable
fun OnboardingScreen(
    onFinished: () -> Unit = {},
    onSkip: () -> Unit = {},
    navController: NavHostController
) {
    var currentPage by remember { mutableIntStateOf(0) }

    when (currentPage) {
        0 -> OnboardingPage1(
            onNextClick = { currentPage = 1 },
            onSkipClick = { navController.navigate(Route.Login) }
        )
        1 -> OnboardingPage2(
            onNextClick = { currentPage = 2 },
            onBackClick = { currentPage = 0 }
        )
        2 -> OnboardingPage3(
            onStartClick = {
                navController.navigate(Route.Login)
            },
            onBackClick = { currentPage = 1 },
            onSecondaryClick = onFinished
        )
        else -> onFinished()
    }
}

// =====================================================================
// PAGE 1: Header + Card Layout
// =====================================================================
@Composable
private fun OnboardingPage1(
    onNextClick: () -> Unit,
    onSkipClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBackground)
    ) {

        // ===== Top Header Section =====
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(HeaderGradientStart, HeaderGradientEnd),
                        start = Offset(0f, 0f),
                        end = Offset(800f, 800f)
                    )
                )
        ) {

            // Decorative blur
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = (-40).dp, y = 80.dp)
                    .size(320.dp)
                    .blur(32.dp)
            )

            // Brand Identity
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AppIconBrand()

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "مصلحتي ",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "خدماتك في مكان واحد",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }

        // ===== Content Card =====
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .offset(y = (-65).dp)
                .shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(40.dp)
                )
                .background(
                    color = CardBackground,
                    shape = RoundedCornerShape(40.dp)
                )
                .padding(32.dp)
        ) {

            Column {

                Page1StepIndicator(activeStep = 0)

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "خدمات حكومية سهلة",
                    color = TextDark,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "أنجز معاملاتك الحكومية بسهولة وسرعة من خلال تطبيق مصلحتي",
                    color = TextGray,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    FeatureCard(
                        title = "آمن وسريع",
                        description = "تشفير كامل لبياناتك الشخصية",
                        iconResId = R.drawable.basma
                    )
                    FeatureCard(
                        title = "ربط مباشر",
                        description = "التحقق عبر منصة مصلحتي\n" +
                                "الموحدة",
                        iconResId = R.drawable.rabt
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // زر التالي
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .background(
                            color = GoldenYellow,
                            shape = RoundedCornerShape(32.dp)
                        )
                        .clickable { onNextClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "التالي",
                            color = GoldenDark,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

               // Spacer(modifier = Modifier.height(24.dp))

           /*     Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "تخطي",
                        color = TextGrayLight,
                        modifier = Modifier.clickable { onSkipClick() }
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))*/
            }
        }

      //  Spacer(modifier = Modifier.height(40.dp))
    }
}

// =====================================================================
// PAGE 2: Vehicle Registration - Editorial Layout
// =====================================================================
@Composable
private fun OnboardingPage2(
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBackground)
    ) {

        // Dark blur circle (bottom-left)
        Box(
            modifier = Modifier
                .offset(x = (-20).dp, y = 510.dp)
                .size(200.dp)
                .blur(20.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.05f),
                    shape = CircleShape
                )
        )

        // Top gradient bleed (editorial accent)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(128.dp)
                .graphicsLayer { alpha = 0.5f }
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.05f),
                            Color.Transparent
                        )
                    )
                )
        )

        // ===== Hero Content =====
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 170.dp, bottom = 164.dp), // leave room for footer
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Circular Orbit Icon
            CircularOrbitIcon()

            Spacer(modifier = Modifier.height(48.dp))

            // Heading
            Text(
                text = "تسجيل المركبات",
                color = Color.Black,
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                letterSpacing = (-0.9).sp,
                lineHeight = 45.sp
            )

            Spacer(modifier = Modifier.height(15.dp))

            // Description
            Text(
                text = "سجّل مركباتك وأدر ملكيتها\nبكل سهولة ويسر",
                color = TextGrayAlpha,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                lineHeight = 29.sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Bento Preview Card
            BentoPreviewCard()
        }

        // ===== Bottom Footer =====
        Page2Footer(
            onNextClick = onNextClick,
            onBackClick = onBackClick
        )
    }
}


// =====================================================================
// PAGE 3: Final Step - Car Transfer / Start
// =====================================================================
@Composable
private fun OnboardingPage3(
    onStartClick: () -> Unit,
    onBackClick: () -> Unit,
    onSecondaryClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBackground)
    ) {
        // ===== Background Abstract Decor (opacity 0.2) =====
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.2f }
        ) {


            // Navy blur circle (left side)
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = 0.dp, y = (-160).dp)
                    .size(192.dp)
                    .blur(30.dp)
                    .background(
                        color = DarkNavyLight,
                        shape = CircleShape
                    )
            )
        }

        // ===== Header - Back Button =====
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(112.dp)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .shadow(
                        elevation = 1.dp,
                        shape = RoundedCornerShape(16.dp),
                        ambientColor = Color.Black.copy(alpha = 0.05f),
                        spotColor = Color.Black.copy(alpha = 0.05f)
                    )
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onBackClick() },
                contentAlignment = Alignment.Center
            ) {
                // Secondary Action
                Text(
                    text = "تخطي",
                    color = TextGray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onSecondaryClick() }
                )
            }
        }

        // ===== Main Content =====
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .padding(top = 112.dp, bottom = 236.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Visual Anchor - Central Car Transfer Box
            CarTransferVisual()

            Spacer(modifier = Modifier.height(48.dp))

            // Typography Cluster
            Text(
                text = "إرسال ونقل الملكية",
                color = TextDark,
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                letterSpacing = (-0.9).sp,
                lineHeight = 45.sp
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "أرسل طلب نقل الملكية للمشتري\n" +
                        "وأتمم العملية بكل أمان وسرعة.",
                color = TextGray,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                lineHeight = 29.sp
            )
        }

        // ===== Footer Controls =====
        Page3Footer(
            onStartClick = onStartClick,
            onSecondaryClick = onSecondaryClick
        )
    }
}





// ============== Page 1 Step Indicator (Bar Style) ==============
@Composable
private fun Page1StepIndicator(activeStep: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(3) { index ->
            val isActive = index == activeStep

            val width by animateDpAsState(
                targetValue = 40.dp,
                animationSpec = tween(300),
                label = "stepWidth"
            )

            val color by animateColorAsState(
                targetValue = if (isActive) GoldenYellow else IndicatorInactive,
                animationSpec = tween(300),
                label = "stepColor"
            )

            Box(
                modifier = Modifier
                    .width(width)
                    .height(6.dp)
                    .then(
                        if (isActive) {
                            Modifier.shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(9999.dp),
                                ambientColor = GoldenYellow.copy(alpha = 0.4f),
                                spotColor = GoldenYellow.copy(alpha = 0.4f)
                            )
                        } else {
                            Modifier
                        }
                    )
                    .background(
                        color = color,
                        shape = RoundedCornerShape(9999.dp)
                    )
            )

            if (index < 2) {
                Spacer(modifier = Modifier.width(12.dp))
            }
        }
    }
}

// ============== Page 2 Step Indicator (Dot Style) ==============
@Composable
fun Page2StepIndicator(activeStep: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(3) { index ->
            val isActive = index == activeStep

            if (isActive) {
                // Active: wider pill shape
                Box(
                    modifier = Modifier
                        .width(32.dp)
                        .height(10.dp)
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(9999.dp),
                            ambientColor = GoldenYellow.copy(alpha = 0.5f),
                            spotColor = GoldenYellow.copy(alpha = 0.5f)
                        )
                        .background(
                            color = GoldenYellow,
                            shape = RoundedCornerShape(9999.dp)
                        )
                )
            } else {
                // Inactive: small circle
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            color = IndicatorInactiveDot,
                            shape = CircleShape
                        )
                )
            }

            if (index < 2) {
                Spacer(modifier = Modifier.width(12.dp))
            }
        }
    }
}


// ============== Page 3 Step Indicator ==============
@Composable
fun Page3StepIndicator(activeStep: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Active step (pill shape)
        repeat(3) { index ->
            val isActive = index == activeStep

            if (isActive) {
                // Active: wider pill shape
                Box(
                    modifier = Modifier
                        .width(32.dp)
                        .height(10.dp)
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(9999.dp),
                            ambientColor = GoldenYellow.copy(alpha = 0.5f),
                            spotColor = GoldenYellow.copy(alpha = 0.5f)
                        )
                        .background(
                            color = GoldenYellow,
                            shape = RoundedCornerShape(9999.dp)
                        )
                )
            } else {
                // Inactive: small circle
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            color = IndicatorInactiveDot,
                            shape = CircleShape
                        )
                )
            }

            if (index < 2) {
                Spacer(modifier = Modifier.width(12.dp))
            }
        }
    }
}

















@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnboardingPage1Preview() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        OnboardingPage1(onNextClick = {}, onSkipClick = {})
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnboardingPage2Preview() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        OnboardingPage2(onNextClick = {}, onBackClick = {})
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnboardingPage3Preview() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        OnboardingPage3(
            onStartClick = {},
            onBackClick = {},
            onSecondaryClick = {}
        )
    }
}
