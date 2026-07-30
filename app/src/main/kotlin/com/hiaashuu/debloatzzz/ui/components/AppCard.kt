package com.hiaashuu.debloatzzz.ui.components

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Android
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hiaashuu.debloatzzz.model.InstalledAppInfo
import com.hiaashuu.debloatzzz.model.SafetyTag

private fun Drawable.toBitmapSafe(): Bitmap? {
    return try {
        if (this is BitmapDrawable && bitmap != null) {
            return bitmap
        }
        val w = if (intrinsicWidth > 0) intrinsicWidth else 64
        val h = if (intrinsicHeight > 0) intrinsicHeight else 64
        val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        setBounds(0, 0, canvas.width, canvas.height)
        draw(canvas)
        bmp
    } catch (e: Exception) {
        null
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AppCard(
    app: InstalledAppInfo,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val iconBitmap = remember(app.packageName) {
        try {
            context.packageManager.getApplicationIcon(app.packageName).toBitmapSafe()?.asImageBitmap()
        } catch (e: Exception) {
            null
        }
    }

    val borderColor = when (app.bloatInfo?.safetyTag) {
        SafetyTag.RECOMMENDED_REMOVE -> MaterialTheme.colorScheme.error.copy(alpha = 0.4f)
        SafetyTag.SAFE_TO_REMOVE -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        SafetyTag.CAUTION -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        SafetyTag.KEEP -> MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
        SafetyTag.REPLACEABLE -> MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f)
        null, SafetyTag.UNKNOWN -> MaterialTheme.colorScheme.outlineVariant
    }

    val cardBackground = if (isSelected) {
        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
    } else {
        MaterialTheme.colorScheme.surface
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        color = cardBackground,
        border = BorderStroke(
            width = if (isSelected) 1.5.dp else 0.8.dp,
            color = if (isSelected) MaterialTheme.colorScheme.primary else borderColor
        ),
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                if (iconBitmap != null) {
                    Image(
                        bitmap = iconBitmap,
                        contentDescription = app.displayName,
                        modifier = Modifier.size(48.dp),
                        contentScale = ContentScale.Fit
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.Android,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = app.displayName,
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = FontFamily.Default,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = app.packageName,
                    style = MaterialTheme.typography.labelSmall,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 9.5.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )

                val sizeMb = if (app.apkSize > 0) String.format(java.util.Locale.US, "%.1f MB", app.apkSize / (1024f * 1024f)) else "N/A"
                val installerName = when (app.installer) {
                    "com.android.vending" -> "Play Store"
                    "com.aurora.store" -> "Aurora Store"
                    "org.fdroid.fdroid" -> "F-Droid"
                    "com.heytap.market" -> "App Market"
                    "com.xiaomi.mipicks" -> "GetApps"
                    "com.sec.android.app.samsungapps" -> "Galaxy Store"
                    "com.amazon.venezia" -> "Amazon Appstore"
                    null -> if (app.isSystemApp) "System" else "Sideloaded"
                    else -> app.installer
                }
                
                Text(
                    text = "v${app.versionName} • $sizeMb • $installerName",
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 9.5.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.85f)
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {

                    val tag = app.bloatInfo?.safetyTag ?: SafetyTag.UNKNOWN
                    SafetyTagChip(tag = tag)

                    if (!app.isEnabled) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f)
                        ) {
                            Text(
                                text = "DISABLED",
                                style = MaterialTheme.typography.labelSmall,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "Selected",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(22.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.ChevronRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}