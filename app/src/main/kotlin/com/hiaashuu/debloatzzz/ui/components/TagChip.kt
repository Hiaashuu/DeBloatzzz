package com.hiaashuu.debloatzzz.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hiaashuu.debloatzzz.model.AppCategory
import com.hiaashuu.debloatzzz.model.SafetyTag
import com.hiaashuu.debloatzzz.ui.theme.InfoBlue
import com.hiaashuu.debloatzzz.ui.theme.PremiumGold
import com.hiaashuu.debloatzzz.ui.theme.SuccessGreen
import com.hiaashuu.debloatzzz.ui.theme.WarningOrange

fun safetyTagLabel(tag: SafetyTag): String {
    return when (tag) {
        SafetyTag.KEEP -> "✅ KEEP — Essential"
        SafetyTag.SAFE_TO_REMOVE -> "🟢 SAFE TO REMOVE"
        SafetyTag.RECOMMENDED_REMOVE -> "🔴 BLOATWARE"
        SafetyTag.CAUTION -> "⚠️ CAUTION"
        SafetyTag.REPLACEABLE -> "🔄 REPLACEABLE"
        SafetyTag.UNKNOWN -> "❓ UNKNOWN APP"
    }
}

fun safetyTagShortLabel(tag: SafetyTag): String {
    return when (tag) {
        SafetyTag.KEEP -> "ESSENTIAL"
        SafetyTag.SAFE_TO_REMOVE -> "SAFE REMOVE"
        SafetyTag.RECOMMENDED_REMOVE -> "BLOATWARE"
        SafetyTag.CAUTION -> "CAUTION"
        SafetyTag.REPLACEABLE -> "REPLACEABLE"
        SafetyTag.UNKNOWN -> "UNKNOWN"
    }
}

fun safetyTagColor(tag: SafetyTag): Color {
    return when (tag) {
        SafetyTag.KEEP -> InfoBlue
        SafetyTag.SAFE_TO_REMOVE -> SuccessGreen
        SafetyTag.RECOMMENDED_REMOVE -> Color(0xFFE53935)
        SafetyTag.CAUTION -> WarningOrange
        SafetyTag.REPLACEABLE -> PremiumGold
        SafetyTag.UNKNOWN -> Color(0xFF78909C)
    }
}

fun categoryLabel(category: AppCategory): String {
    return when (category) {
        AppCategory.SYSTEM_CRITICAL -> "System Critical"
        AppCategory.SAMSUNG_BLOAT -> "Samsung"
        AppCategory.GOOGLE_SERVICE -> "Google Service"
        AppCategory.GOOGLE_BLOAT -> "Google"
        AppCategory.XIAOMI_MIUI -> "Xiaomi / MIUI"
        AppCategory.ONEPLUS_OPPO_REALME -> "OnePlus / OPPO / Realme"
        AppCategory.STOCK_ANDROID -> "Stock Android"
        AppCategory.CARRIER_BLOAT -> "Carrier"
        AppCategory.SOCIAL_MEDIA -> "Social Media"
        AppCategory.UTILITY -> "Utility"
        AppCategory.COMMUNICATION -> "Communication"
        AppCategory.OEM_SERVICE -> "OEM Service"
    }
}

@Composable
fun SafetyTagChip(
    tag: SafetyTag,
    modifier: Modifier = Modifier
) {
    val color = safetyTagColor(tag)
    val label = safetyTagShortLabel(tag)
    val shape = RoundedCornerShape(6.dp)

    Surface(
        modifier = modifier
            .border(width = 1.dp, color = color.copy(alpha = 0.6f), shape = shape),
        shape = shape,
        color = color.copy(alpha = 0.12f)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
            Text(
                text = label,
                color = color,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.3.sp
            )
        }
    }
}

@Composable
fun CategoryChip(
    category: AppCategory,
    modifier: Modifier = Modifier
) {
    val label = categoryLabel(category)
    val color = MaterialTheme.colorScheme.tertiary
    val shape = RoundedCornerShape(6.dp)

    Surface(
        modifier = modifier
            .border(width = 1.dp, color = color.copy(alpha = 0.5f), shape = shape),
        shape = shape,
        color = color.copy(alpha = 0.1f)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
            Text(
                text = label,
                color = color,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}