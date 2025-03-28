package com.adrifernandev.marsroverchallenge.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.adrifernandev.marsroverchallenge.designsystem.R

@Composable
private fun robotoFontFamily() = FontFamily(
    Font(R.font.roboto_condensed_light, weight = FontWeight.Light),
    Font(R.font.roboto_condensed_regular, weight = FontWeight.Normal),
    Font(R.font.roboto_condensed_medium, weight = FontWeight.Medium),
    Font(R.font.roboto_condensed_semibold, weight = FontWeight.SemiBold),
    Font(R.font.roboto_condensed_bold, weight = FontWeight.Bold),
)

@Composable
fun dsTypography() = Typography().run {
    val fontFamily = robotoFontFamily()
    copy(
        // Impactful titles (e.g., landing page titles)
        displayLarge = displayLarge.copy(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal
        ),
        // Large titles, but less dominant than displayLarge (e.g., section headers)
        displayMedium = displayMedium.copy(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal
        ),
        // Medium-large titles, typically for smaller sections
        displaySmall = displaySmall.copy(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal
        ),

        // Large headlines for key sections (e.g., page headers)
        headlineLarge = headlineLarge.copy(
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold
        ),
        // Medium headlines for secondary section titles
        headlineMedium = headlineMedium.copy(
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold
        ),
        // Small headlines for tertiary section titles
        headlineSmall = headlineSmall.copy(
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold
        ),

        // Large titles for secondary content or subtitles
        titleLarge = titleLarge.copy(
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold
        ),
        // Medium titles, often used for subtitles or minor headings
        titleMedium = titleMedium.copy(
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold
        ),
        // Small titles for captions or less important headings
        titleSmall = titleSmall.copy(
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold
        ),

        // Primary body text, used for main content or paragraphs
        bodyLarge = bodyLarge.copy(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal
        ),
        // Secondary body text, often used for side content or less important text
        bodyMedium = bodyMedium.copy(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal
        ),
        // Small body text, useful for notes or disclaimers
        bodySmall = bodySmall.copy(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal
        ),
        // Large labels, typically used in large buttons or UI elements
        labelLarge = labelLarge.copy(
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold
        ),
        // Medium labels, used for smaller buttons or input fields
        labelMedium = labelMedium.copy(
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold
        ),
        // Small labels, often used for chips or very small UI elements
        labelSmall = labelSmall.copy(
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold
        )
    )
}