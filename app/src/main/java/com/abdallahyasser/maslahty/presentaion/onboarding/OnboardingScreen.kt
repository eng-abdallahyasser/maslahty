package com.abdallahyasser.maslahty.presentaion.onboarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abdallahyasser.maslahty.R
import com.abdallahyasser.maslahty.theme.BorderColor
import com.abdallahyasser.maslahty.theme.CardBackground
import com.abdallahyasser.maslahty.theme.DarkNavyLight
import com.abdallahyasser.maslahty.theme.FeatureCardBg
import com.abdallahyasser.maslahty.theme.GoldenDark
import com.abdallahyasser.maslahty.theme.GoldenYellow
import com.abdallahyasser.maslahty.theme.HeaderGradientEnd
import com.abdallahyasser.maslahty.theme.HeaderGradientStart
import com.abdallahyasser.maslahty.theme.IconBrown
import com.abdallahyasser.maslahty.theme.IndicatorInactive
import com.abdallahyasser.maslahty.theme.IndicatorInactiveDot
import com.abdallahyasser.maslahty.theme.PageBackground
import com.abdallahyasser.maslahty.theme.SkeletonDark
import com.abdallahyasser.maslahty.theme.SkeletonLight
import com.abdallahyasser.maslahty.theme.SuccessGreen
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
    onSkip: () -> Unit = {}
) {
    var currentPage by remember { mutableIntStateOf(0) }

    when (currentPage) {
        0 -> OnboardingPage1(
            onNextClick = { currentPage = 1 },
            onSkipClick = onSkip
        )
        1 -> OnboardingPage2(
            onNextClick = { currentPage = 2 },
            onBackClick = { currentPage = 0 }
        )
        2 -> OnboardingPage3(
            onStartClick = onFinished,
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
     // make cloumn scrollable in case of smaller screens, but for the preview we can keep it static
    // .verticalScroll(rememberScrollState())
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

// ============== Circular Orbit Icon ==============
@Composable
private fun CircularOrbitIcon() {

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

// ============== Bento Preview Card ==============
@Composable
private fun BentoPreviewCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(24.dp),
                ambientColor = Color.Black.copy(alpha = 0.04f),
                spotColor = Color.Black.copy(alpha = 0.04f)
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(24.dp)
            )
            .border(
                width = 1.dp,
                color = BorderColor.copy(alpha = 0.1f),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Left icon placeholder
        Box(
            modifier = Modifier.size(20.dp),
            contentAlignment = Alignment.Center
        ) {
            val icon = ImageVector.vectorResource(id = R.drawable.camera)
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = IconBrown,
                modifier = Modifier.size(20.dp)
            )
        }

        // Skeleton text lines (center)
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Skeleton line 1
            Box(
                modifier = Modifier
                    .width(96.dp)
                    .height(8.dp)
                    .background(
                        color = SkeletonDark,
                        shape = RoundedCornerShape(9999.dp)
                    )
            )
            // Skeleton line 2
            Box(
                modifier = Modifier
                    .width(128.dp)
                    .height(6.dp)
                    .background(
                        color = SkeletonLight,
                        shape = RoundedCornerShape(9999.dp)
                    )
            )
        }

        // Right icon box
        Box(
            modifier = Modifier
                .size(46.dp, 44.dp)
                .background(
                    color = FeatureCardBg,
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            val carIcon = ImageVector.vectorResource(id = R.drawable.right2)
            Icon(
                imageVector = carIcon,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(22.dp, 20.dp)
            )
        }
    }
}

// ============== Page 2 Footer ==============
@Composable
private fun Page2Footer(
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White.copy(alpha = 0.8f)
                )
                .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Step Indicator (dot style)
            Page2StepIndicator(activeStep = 1)

            // Action Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Previous Button (Ghost)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(58.dp)
                        .border(
                            width = 1.dp,
                            color = BorderColor.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(10.dp)
                        )


                        Text(
                            text = "السابق",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 24.sp
                        )

                    }
                }



                // Next Button (Primary)
                Box(
                    modifier = Modifier
                        .weight(1.6f)
                        .height(57.dp)
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(16.dp),
                            ambientColor = GoldenYellow.copy(alpha = 0.3f),
                            spotColor = GoldenYellow.copy(alpha = 0.3f)
                        )
                        .background(
                            color = GoldenYellow,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onNextClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "التالي",
                            color = GoldenDark,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 24.sp
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = GoldenDark,
                            modifier = Modifier.size(10.dp)
                        )

                    }
                }


            }
        }
    }
}

// =====================================================================
// SHARED COMPONENTS
// =====================================================================

// ============== App Icon Brand Component (Page 1) ==============
@Composable
private fun AppIconBrand() {
    Box(
        modifier = Modifier.size(128.dp),
        contentAlignment = Alignment.Center
    ) {
        // Rotated golden background square
        Box(
            modifier = Modifier
                .size(128.dp)
                .offset(x = (-12).dp, y = (-12).dp)
                .graphicsLayer { rotationZ = 12f }
                .background(
                    color = GoldenYellow.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(24.dp)
                )
        )

        // Glass-morphism card
        Box(
            modifier = Modifier
                .size(128.dp)
                .shadow(
                    elevation = 25.dp,
                    shape = RoundedCornerShape(24.dp),
                    ambientColor = Color.Black.copy(alpha = 0.25f),
                    spotColor = Color.Black.copy(alpha = 0.25f)
                )
                .background(
                    color = Color.White.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(24.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            // Main icon
            val image = ImageVector.vectorResource(id = R.drawable.icoon)
            Icon(
                imageVector = image,
                contentDescription = "App Icon",
                tint = GoldenYellow,
                modifier = Modifier.size(50.dp)
            )
        }

        // Small badge at bottom-right
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 7.dp, y = 7.dp)
                .size(36.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = CircleShape,
                    ambientColor = Color.Black.copy(alpha = 0.1f),
                    spotColor = Color.Black.copy(alpha = 0.1f)
                )
                .background(
                    color = GoldenYellow,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        color = GoldenYellow,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✓",
                    color = GoldenDark,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
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
private fun Page2StepIndicator(activeStep: Int) {
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

// ============== Feature Card (Page 1) ==============
@Composable
private fun FeatureCard(
    title: String,
    description: String,
    iconResId: Int

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = FeatureCardBg,
                shape = RoundedCornerShape(32.dp)
            )
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Icon container
        Box(
            modifier = Modifier
                .size(44.dp)
                .shadow(
                    elevation = 1.dp,
                    shape = RoundedCornerShape(24.dp),
                    ambientColor = Color.Black.copy(alpha = 0.05f),
                    spotColor = Color.Black.copy(alpha = 0.05f)
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(24.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(iconResId),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                contentScale = ContentScale.Fit
            )
        }

        // Text content
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = title,
                color = TextDark,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Right,
                lineHeight = 24.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = description,
                color = TextGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Right,
                lineHeight = 20.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
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

// ============== Car Transfer Visual ==============
@Composable
private fun CarTransferVisual() {
    Box(
        contentAlignment = Alignment.Center
    ) {
        // Central dark gradient square
        Box(
            modifier = Modifier
                .size(256.dp)
                .shadow(
                    elevation = 25.dp,
                    shape = RoundedCornerShape(48.dp),
                    ambientColor = Color.Black.copy(alpha = 0.25f),
                    spotColor = Color.Black.copy(alpha = 0.25f)
                )
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(DarkNavyLight, HeaderGradientEnd),
                        start = Offset(0f, 256f),
                        end = Offset(256f, 0f)
                    ),
                    shape = RoundedCornerShape(48.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            // Abstract texture overlay
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .graphicsLayer { alpha = 0.1f }
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.White,
                                Color.Transparent
                            ),
                            center = Offset(0f, 0f),
                            radius = 400f
                        ),
                        shape = RoundedCornerShape(48.dp)
                    )
            )

            // Content inside the dark square
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Top row: two glass cards with transfer dots
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Left glass card (car icon)
                    GlassCard(
                        width = 61,
                        height = 58,
                        iconSize = 27 to 24,
                        icon = ImageVector.vectorResource(id = R.drawable.person)
                    )

                    // Transfer dots
                    TransferDots()

                    // Right glass card (document icon)
                    GlassCard(
                        width = 58,
                        height = 58,
                        iconSize = 24 to 24,
                        icon = ImageVector.vectorResource(id = R.drawable.car)
                    )
                }

                // Car icon below
                Icon(
                    painter = painterResource(id = R.drawable.confirm),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(50.dp, 45.dp)
                )
            }
        }

        // Floating Card (bottom-right)
        FloatingStatusCard(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 8.dp, y = 16.dp)
        )
    }
}

// ============== Glass Card ==============
@Composable
private fun GlassCard(
    width: Int,
    height: Int,
    iconSize: Pair<Int, Int>,
    icon: ImageVector

) {
    Box(
        modifier = Modifier
            .size(width.dp, height.dp)
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.1f),
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                color = Color.White.copy(alpha = 0.1f),
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = GoldenYellow,
            modifier = Modifier.size(iconSize.first.dp, iconSize.second.dp)
        )
    }
}

// ============== Transfer Dots ==============
@Composable
private fun TransferDots() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(
                    color = GoldenYellow.copy(alpha = 0.3f),
                    shape = CircleShape
                )
        )
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(
                    color = GoldenYellow.copy(alpha = 0.6f),
                    shape = CircleShape
                )
        )
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(
                    color = GoldenYellow,
                    shape = CircleShape
                )
        )
    }
}

// ============== Floating Status Card ==============
@Composable
private fun FloatingStatusCard(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .width(160.dp)
            .shadow(
                elevation = 20.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color.Black.copy(alpha = 0.1f),
                spotColor = Color.Black.copy(alpha = 0.1f)
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                color = Color.White.copy(alpha = 0.9f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "عملية آمنة",
                color = TextGray,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Right,
                lineHeight = 15.sp
            )
            Text(
                text = "مشفر بالكامل",
                color = TextDark,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Right,
                lineHeight = 16.sp
            )
        }

        // Green success circle
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = SuccessGreen.copy(alpha = 0.2f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.right),
                contentDescription = null,
                tint = Color(0xFF16A34A),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

// ============== Page 3 Step Indicator ==============
@Composable
private fun Page3StepIndicator(activeStep: Int) {
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

// ============== Page 3 Footer ==============
@Composable
private fun Page3Footer(
    onStartClick: () -> Unit,
    onSecondaryClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(236.dp)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Step Indicator
            Page3StepIndicator(activeStep = 2)

            Spacer(modifier = Modifier.height(32.dp))

            // Primary CTA Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp),
                        ambientColor = GoldenYellow.copy(alpha = 0.25f),
                        spotColor = GoldenYellow.copy(alpha = 0.25f)
                    )
                    .background(
                        color = GoldenYellow,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onStartClick() },
                contentAlignment = Alignment.Center
            ) {
                // Inner glow line at top
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .align(Alignment.TopCenter)
                        .background(Color.White.copy(alpha = 0.3f))
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "ابدأ الآن",
                        color = GoldenDark,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 28.sp
                    )

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = GoldenDark,
                        modifier = Modifier.size(16.dp)
                    )

                }
            }

            Spacer(modifier = Modifier.height(32.dp))


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
