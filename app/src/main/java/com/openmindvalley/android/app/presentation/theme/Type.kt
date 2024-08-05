package com.openmindvalley.android.app.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        color = TextHeaderPrimary,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    )
)

val Typography.RootTitle: TextStyle
    get() = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight(900),
        fontSize = 32.sp,
        color = TextHeaderPrimary,
        lineHeight = 44.sp,
        letterSpacing = 1.sp
    )
val Typography.SecondaryRootTitle: TextStyle
    get() = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight(800),
        fontSize = 24.sp,
        color = TextHeaderPrimary,
        lineHeight = 32.sp,
        letterSpacing = 1.sp
    )

val Typography.SecondaryRootTitleWhite: TextStyle
    get() = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight(700),
        fontSize = 24.sp,
        color = TextPrimary,
        lineHeight = 32.sp,
        letterSpacing = 1.sp
    )


val Typography.ThumbnailTitle: TextStyle
    get() = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        color = TextPrimary,
        lineHeight = 30.sp,
        letterSpacing = 1.sp
    )

val Typography.ThumbnailSubtitle: TextStyle
    get() = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = TextSecondary,
        lineHeight = 22.sp,
        letterSpacing = 1.sp
    )

val Typography.ThumbnailSubtitleSecondary: TextStyle
    get() = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight(600),
        fontSize = 14.sp,
        color = TextHeaderSecondary,
        lineHeight = 24.sp,
        letterSpacing = 1.sp
    )
