package com.abdallahyasser.maslahty.presentaion.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.abdallahyasser.maslahty.R
import com.abdallahyasser.maslahty.theme.BorderColor
import com.abdallahyasser.maslahty.theme.FeatureCardBg
import com.abdallahyasser.maslahty.theme.IconBrown
import com.abdallahyasser.maslahty.theme.SkeletonDark
import com.abdallahyasser.maslahty.theme.SkeletonLight

// ============== Bento Preview Card ==============
@Composable
public fun BentoPreviewCard() {
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